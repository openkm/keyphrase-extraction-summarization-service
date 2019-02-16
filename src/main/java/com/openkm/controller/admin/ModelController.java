package com.openkm.controller.admin;

/*-
 * #%L
 * kea-summarization
 * %%
 * Copyright (C) 2018 - 2019 OpenKM Knowledge Management System S.L.
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

import com.openkm.config.Config;
import com.openkm.kea.RDFREpository;
import com.openkm.kea.model.ModelBuilder;
import com.openkm.util.FileUtils;
import com.openkm.util.PrincipalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/admin/model")
public class ModelController {
    private static Logger log = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private Config config;

    @Autowired
    private RDFREpository rdfREpository;

    @GetMapping("/home")
    public String home(Model model) throws IOException {
        model.addAttribute("trainingPath", config.getTrainingFolderPath());
        model.addAttribute("vocabularyFile", config.SKOS_FILE);
        model.addAttribute("vocabularyType", config.VOCABULARY_TYPE);
        model.addAttribute("stopwordFile", config.STOP_WORDS_FILE);
        model.addAttribute("modelFile", config.MODEL_FILE);
        model.addAttribute("stemmerClass", config.STEMMER_CLASS);
        model.addAttribute("stopwordClass", config.STOPWORD_CLASS);
        model.addAttribute("language", config.LANGUAGE);
        model.addAttribute("documentEncoding", config.DOCUMENT_ENCODING);
        model.addAttribute("numerOfTrainingFiles", getNumberOfTrainingFiles());
        model.addAttribute("numerOfModelFiles", getNumberOfModelFiles());

        return "admin/model/index";
    }

    @GetMapping("/create")
    public String create(Model model) throws IOException {
        String trainingFilesPath = config.getTrainingFolderPath();
        // Create temp model based on current model name
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String modelFilePath = config.getModelsFolderPath() + File.separator + sdf.format(Calendar.getInstance().getTime()) + ".model";
        String vocabularyFilePath = config.SKOS_FILE;
        String stemerClassName = config.STEMMER_CLASS;
        String vocabularyType = config.VOCABULARY_TYPE;
        String documentEncoding = config.DOCUMENT_ENCODING;
        String lang = config.LANGUAGE;
        String stopwordsClassName = config.STOPWORD_CLASS;
        String stopwordsFile = config.STOP_WORDS_FILE;
        String testingFoder = "";

        model.addAttribute("trainingPath", trainingFilesPath);
        model.addAttribute("vocabularyFile", vocabularyFilePath);
        model.addAttribute("vocabularyType", vocabularyType);
        model.addAttribute("stopwordFile", stopwordsFile);
        model.addAttribute("modelFile", modelFilePath);
        model.addAttribute("stemmerClass", stemerClassName);
        model.addAttribute("stopwordClass", stopwordsClassName);
        model.addAttribute("language", lang);
        model.addAttribute("documentEncoding", documentEncoding);
        model.addAttribute("numerOfTrainingFiles", getNumberOfTrainingFiles());
        model.addAttribute("numerOfModelFiles", getNumberOfModelFiles());

        ModelBuilder.createModel(trainingFilesPath, modelFilePath, vocabularyFilePath, stemerClassName, vocabularyType, documentEncoding,
                lang, stopwordsClassName, stopwordsFile, testingFoder);

        return "admin/model/index";
    }

    @GetMapping("/cleanTrainig")
    public ModelAndView cleanTrainig(Model model) throws IOException {
        FileUtils.deleteQuietly(new File(config.getTrainingFolderPath()));
        new File(config.getTrainingFolderPath()).mkdirs();
        return new ModelAndView("redirect:/admin/model/home");
    }

    @GetMapping("/cleanModels")
    public ModelAndView cleanModels(Model model) throws IOException {
        FileUtils.deleteQuietly(new File(config.getModelsFolderPath()));
        new File(config.getModelsFolderPath()).mkdirs();
        return new ModelAndView("redirect:/admin/model/home");
    }

    @GetMapping("/list")
    public String list(Model model) throws IOException {
        List<String> modelFiles = new ArrayList<>();
        Files.list(Paths.get(config.getModelsFolderPath()))
                .filter(p -> p.toFile().getAbsolutePath().endsWith("model")).forEach(path -> modelFiles.add(path.getFileName().toString()));
        model.addAttribute("modelFiles", modelFiles);
        return "admin/model/list";
    }

    @GetMapping("/enableModel")
    public ModelAndView enableModel(String modelFileName) throws IOException {
        String modelFilePath = config.getModelsFolderPath() + File.separator + modelFileName;
        if (FileUtils.existFile(modelFilePath)) {
            FileUtils.copy(new File(modelFilePath), new File(config.MODEL_FILE));
            rdfREpository.RDFREpositoryInit();
        }
        return new ModelAndView("redirect:/admin/model/home");
    }

    private long getNumberOfTrainingFiles() throws IOException {
        long count = Files.list(Paths.get(config.getTrainingFolderPath()))
                .filter(p -> p.toFile().getAbsolutePath().endsWith("key"))
                .count();
        return count;
    }

    private long getNumberOfModelFiles() throws IOException {
        long count = Files.list(Paths.get(config.getModelsFolderPath()))
                .filter(p -> p.toFile().getAbsolutePath().endsWith("model"))
                .count();
        return count;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFound(Exception ex) {
        log.error("Not found", ex);
    }
}
