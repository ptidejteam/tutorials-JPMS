# tutorials-JPMS

Tutorial and hackathon on the Java Platform Module System. The different branches are the different steps of the hackathon.

The branches are:

- [1-complete-overview](https://github.com/ptidejteam/tutorials-JPMS/tree/1-complete-overview), which contains code to show all the different possible combinations of projects/modules, requires/exports, and classpath/modulepath. Used to produce the table in the slides.
- [2-simple-example-CLOZE](https://github.com/ptidejteam/tutorials-JPMS/tree/2-simple-example-CLOZE), which contains the code of a simple example with three projects and one library. Starting point of the first hackathon: the goal is to convert each project into a module.
- [3-simple-example-SOLVE-JPMS](https://github.com/ptidejteam/tutorials-JPMS/tree/3-simple-example-SOLVE-JPMS), which contains the code of the simple example with all projects converted into modules. It doesn't use Maven.
- 4-simple-example-SOLVE-JPMS-Maven, which contains the code of the simple example with all projects converted into modules and uses Maven to manage dependencies.
- [5-mini-Pridej-No-JPMS](https://github.com/ptidejteam/tutorials-JPMS/tree/5-mini-Pridej-No-JPMS), which contains a subset of the Ptidej project. Dependencies are managed using Maven, but there are no modules. Starting point of the second hackathon: the goal is to convert each project into a module.
- 6-mini-Ptidej-with-JPMS, which contains the code of the subset of the Ptidej project in which each project is a module and dependencies are managed by Maven.
