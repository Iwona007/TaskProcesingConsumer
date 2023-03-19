# TaskProcessingConsumer
## About project
This id a demo application - consumer part - for CDQ interview purposes.

## Prerequisites
The following tools are required to start the application:
- [IntelliJ IDEA](https://www.jetbrains.com/idea/) / [VSC](https://code.visualstudio.com/) / [Eclipse](https://www.eclipse.org/)
- [Java 17 LTS](https://openjdk.org/projects/jdk/17/)
- [MySql Workbench](https://www.mysql.com/products/workbench/) / [DBeaver](https://dbeaver.io/)
- [Git Bash](https://git-scm.com/downloads)
- [Maven 3.x](https://maven.apache.org/download.cgi)
- [Postman](https://www.postman.com/)
- [Docker](https://docs.docker.com/get-docker/) - please refer to [Setting up Docker]()

## How to run

### 1. Clone the repository
Please clone the repository by https or ssh (below I used the https method).
```
git clone https://github.com/Iwona007/TaskProcesingConsumer.git
```
### 2. Run docker
When docker from TaskProcessingProducer is running, the TaskProcessingConsumer can be started from IDEA

## 3 How it works
When TaskProcessingProducer sent post request, please check log consumer log console to see message for examle:
taskEvent: Task(taskId=1, input=ADBCDEF, pattern=ABC, taskType=NEW, result=ABC, ADBCDEF, status=0%) 
