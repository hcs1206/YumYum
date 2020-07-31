# YUMYUM website

## 프로젝트 요약

### 설명

- 

### 이용한 프레임워크 및 언어

- Spring boot, JDBC, MyBatis
- Vue.js, Vuetify
- MySQL
- AWS EC2
- Docker
- Jenkins

### 메인화면

![image-20200731141615700](C:\Users\pc\AppData\Roaming\Typora\typora-user-images\image-20200731141615700.png)



## 코드 설명

## Spring

### com.redbeans.yumyum.controller.StoreController

|      API명      | 메서드 |                     요청 URL                     | 포맷 |
| :-------------: | :----: | :----------------------------------------------: | :--: |
|  findAllStores  |  GET   |      http://{Person ip}:8080/api/store/all       | JSON |
| findStoreByName |  GET   |  http://{Personip}:8080/api//store/find/{name}   | JSON |
| findStoreDetail |  GET   |  http://{Person ip}:8080/api//store/detail/{id}  | JSON |
| recommendStores |  GET   | http://{Person ip}:8080/api/recommendStores/{id} | JSON |

#### findAllStore

##### Request

- None

##### Reponse

- ResponseEntity<List<Store>>

##### Summary

- 모든 음식점 정보를 가져옵니다.



#### findStoreByName

##### Request

- String name

##### Reponse

- ResponseEntity<List<Store>>

##### Summary

- 해당 키워드를 포함한 음식점 정보를 가져옵니다.



#### findStoreDetail

##### Request

- String id

##### Reponse

- RResponseEntity<Detail>

##### Summary

- id에 해당하는 음식점의 상세정보를 가져옵니다.



#### recommendStores

##### Request

- String id

##### Reponse

- ResponseEntity<List<Store>>

##### Summary

- id와 가장 연관이 높고 이용자 만족도가 높은 음식점을 가져옵니다.



### com.redbeans.yumyum.dto

#### Store

- 음식점 정보가 담겨있는 Data Transfer Object
- **Variables**
  String id
  String name
  String url
  String address
  int reviewCount
  int lat
  int lng

#### Detail

- 음식점 상세 정보가 담겨있는 Data Transfer Object
- **Variables**
  String id
  String name
  String phone
  String doro
  String jibun
  String time
  String menu1
  String cost1
  String menu2
  String cost2
  String contents

#### Review

- 리뷰정보가 담겨있는 Data Transfer Object
- **Variables**
  String id
  String userName
  String date
  int score
  int visitCount

### com.redbeans.yumyum.service

- 비즈니스 로직이 들어가는 부분

### com.redbeans.yumyum.dao

- DB를 사용해 데이터를 조회하는 부분



## Docker, jenkins, git 연동

### 도커란?

도커는 컨테이너 기반의 오픈소스 가상화 플랫폼 

다양한 프로그램, 실행환경을 컨테이너로 추상화하고 동일한 인터페이스를 제공하여 프로그램의 

배포 및 관리를 단순하게 해준다. 백엔드 프로그램, 데이터베이스 서버, 메시지 큐 등 어떤 프로그램도 

컨테이너로 추상화할 수 있고 조립PC, AWS, Azure, Google cloud등 어디에서든 실행할 수 있다. 

**이미지, 컨테이너?** 

이미지 : 이미지는 컨테이너 실행에 필요한 파일과 설정값등을 포함하고 있는 것으로 상태값을 가지 지 않고 변하지 않는다. 

컨테이너 : 이미지를 “실행한 상태”라고 볼 수 있고 추가되거나 변하는 값은 컨테이너에 저장된다.

![image-20200731141953035](C:\Users\pc\AppData\Roaming\Typora\typora-user-images\image-20200731141953035.png)

### EC2에 docker 설치하기

1. **자동 스크립트 사용하여 설치**

   curl -fsSL https://get.docker.com/ | sudo sh

   

2. **sudo 없이 사용하기**

   - docker는 기본적으로 root권한이 필요하다. root가 아닌 사용자가 sudo 없이 사용하려면 

     해당 사용자를 docker그룹에 추가

   sudo usermod -aG docker $USER # 현재 접속중인 사용자에게 권한주기



3. **설치 확인**
   docker version



4. **이미지 다운**

   - 컨테이너를 실행할 때 해당 이미지가 없으면 자동 설치하지만 이미지만 따로 설치하고 싶을 때 다음 명령어 사용. 여기서는 jenkins 이미지를 설치하였다.

   docker pull [이미지이름]:[버전]



5. **이미지 목록 보기 및 삭제**

   - 이미지가 많으면 성능 저하되므로 필요한 이미지만 남기고 관리한다.

   docker images 

   docker rmi [이미지 id]



6. **컨테이너 실행**

   - -d : 백그라운드 모드 

   - --name : 컨테이너 별칭 생성 옆의 jenkins가 별칭 

   - -p : 호스트와 컨테이너의 포트 연결, 도커를 설치한 호스트의 8080포트를, 컨테이너의 

     8080번 포 트에 연결한다는 뜻. MYSQL 경우의 컨테이너를 사용할 때 8080:3306 같은 

     식으로 사용해야함

   docker run -d --name jenkins -p 8080:8080 jenkins/jenkins:lts



7. **컨테이너 목록 보기, 시작, 재시작, 정지**

   - 현재 실행 중인 컨테이너 목록을 출력한다. 실행을 안 시키면 목록에서 안보이는 것 주의!

   docker ps

   docker start [컨테이너 id 또는 name] 

   docker restart [컨테이너 id 또는 name] 

   docker stop [컨테이너 id 또는 name]



8. **컨테이너 제거하기**

   - 중지된 것만 제거 가능 

     docker rm [컨테이너 id] 

   - 중지된 컨테이너 일괄 제거 

     docker rm -v $(docker ps -a -q -f status=exited)



9. **컨테이너 내부 확인하기**

   docker exec -it jenkins /bin/bash



### Jenkins란?

젠킨스는 소프트웨어 개발 시 지속적으로 통합 서비스를 제공하는 툴이다. CI(Continuous Integration) 툴 이라고 표현한다.

 다수의 개발자들이 하나의 프로그램을 개발할 때 버전 충돌을 방지하기 위해 각자 작업한 내용을 공유영역에 있는 저장소에 빈번히 업로드함으로써 지속적 통합이 가능하도록 해준다. 

**젠킨스가 주는 이점** 

- 프로젝트 표준 컴파일 환경에서의 컴파일 오류 검출 
- 자동화 테스트 수행 
- 정적 코드 분석에 의한 코딩 규약 준수여부 체크 
- 프로파일링 툴을 이용한 소스 변경에 따른 성능 변화 감시 
- 결합 테스트 환경에 대한 배포작업



**각종 배치 작업의 간략화** 

 프로젝트 기간 중에 개발자들은 순수한 개발 작업 이외에 DB셋업이나 환경설정, Deploy작업과 같은 단순 작업에 시간과 노력을 들이는 경우가 빈번하다. 데이터베이스의 구축, 어플리케이션 서버로의 Deploy, 라이브러리 릴리즈와 같이 이전에 CLI로 실행되던 작업들이 젠킨스 덕분에 웹 인터페이 스로 손쉽게 가능해졌다. 

**Build 자동화의 확립** 

 빌드 툴의 경우 Java는 maven과 gradle이 자리잡고 있으며, 이미 빌드 관리 툴을 이용해 프로젝트를 진행하고 있다면 젠킨스를 사용하지 않을 이유가 하나도 없다. 젠킨스와 연동하여 빌드 자동화를 통해 프로젝트 진행의 효율성을 높일 수 있다. 

**자동화 테스트** 

 자동화 테스트는 젠킨스를 사용해야 하는 가장 큰 이유 중 하나이며, 사실상 자동화 테스트가 포함되지 않은 빌드는 CI자체가 불가능하다고 봐도 무방하다. 젠킨스는 Subversion이나 Git과 같은 버전관리시스템과 연동하여 코드 변경을 감지하고 자동화 테스트를 수행하기 때문에 만약 개인이 미처 실시하지 못한 테스트가 있다 하여도 든든한 안전망이 되어준다. 제대로 테스트를 거치지 않은 코드 를 커밋하게 되면 화난 젠킨스를 만나게 된다. 

**코드 표준 준수여부 검사** 

 자동화 테스트와 마찬가지로 개인이 미처 실시하지 못한 코드 표준 준수 여부의 검사나 정적 분석을 통한 코드 품질 검사를 빌드 내부에서 수행함으로써 기술적 부채의 감소에도 크게 기여한다. 

**빌드 파이프라인 구성** 

 2개 이상의 모듈로 구성되는 레이어드 아키텍처가 적용 된 프로젝트에는 그에 따는 빌드 파이프라 인 구성이 필요하다. 예를 들면, 도메인 -> 서비스 -> UI와 같이 각 레이어의 참조 관계에 따라 순 차적으로 빌드를 진행하지 않으면 안된다. 젠킨스에서는 이러한 빌드 파이프라인의 구성을 간단히 할 수 있으며, 스크립트를 통해서 매우 복잡한 제어까지도 가능하다



### Jenkins 설정하기

