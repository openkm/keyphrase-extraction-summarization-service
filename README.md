# OpenKM keyphrase extraction summarization service

**Keywords and keyphrases** (multi-word units) are widely used in large document collections.

They describe the content of single documents and provide a kind of semantic metadata that is useful for a wide variety of purposes.

In libraries professional indexers select keyphrases from a controlled vocabulary (also called Subject Headings) according to defined cataloguing rules. On the Internet, digital libraries, or any depositories of data also use keyphrases (or here called content tags or content labels) to organize and provide a thematic access to their data.

**[KEA](http://community.nzdl.org/kea/)** is an algorithm for extracting keyphrases from text documents. It can be either used for **free indexing** or for **indexing with a controlled vocabulary** build from The University of Waikato in the Digital Libraries and Machine Learning Labs of the Computer Science Department by **[Eibe Frank](http://www.cs.waikato.ac.nz/%7Eeibe/)** and **[Olena Medelyan](http://www.medelyan.com/)**

OpenKM keyphrase extraction summarization service is an open-source software distributed under the **[GNU Affero General Public License](https://www.gnu.org/licenses/agpl-3.0.en.html)**. 

## KEAS working
This video shows how KEA Summarization works:

[![Automatic Keyphrase Extraction Summarization](https://i.ytimg.com/vi/-3rFk_xnGtQ/hqdefault.jpg)](https://www.youtube.com/watch?v=T0jKvm3CcpY "Automatic Keyphrase Extraction Summarization")

[Spanish version](https://www.youtube.com/watch?v=-3rFk_xnGtQ)

## Building from Source
```sh
$ git clone [git-repo-url] openkm-community
$ cd openkm-community
$ mvn clean package
```

## Documentation
 * [KEA Summarization](https://docs.openkm.com/kcenter/view/keas/)
 * [Setup](https://docs.openkm.com/kcenter/view/keas/setup.html)
 * [User guide](https://docs.openkm.com/kcenter/view/keas/user-guide.html)
 

## Reporting issues
KEA Summarization service is supported by developers and technical enthusiasts via [the forum](http://forum.openkm.com) of the user community. If you want to raise an issue, please follow the below recommendations:
 * Before you post a question, please search the question to see if someone has already reported it / asked for it.
 * If the question does not already exist, create a new post. 
 * Please provide as much detailed information as possible with the issue report. We need to know the version of OpenKM, Operating System, browser and whatever you think might help us to understand the problem or question.


## License
[KEA Summarization](https://www.openkm.com/en/open-source-document-management-system.html) is available to Open Source community under the [GNU Affero General Public License](https://www.gnu.org/licenses/agpl-3.0.en.html).
The OpenKM source code is available for the entire community, which is free to use, modify and redistribute under the premises of such license.
