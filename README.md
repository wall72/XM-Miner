# XM-Miner
Scenario based Data Mining (Machine Learning) Tool

## 1.	XM-Miner 개요
XM-Miner는 기존의 Mining Tool 과는 차별화 된 비즈니스 영역별, 도메인 중심적인 마이닝 도구로서 문제 중심적인 의사결정(Decision Making)과 CRM 전략(Strategy)을 수립하는데 매우 효과 적으로 사용될 수 있다.

### 1.1 개발 정보
+ 개발시기: 2000 ~ 2001년 초
+ 개발참여: 대우정보시스템, 연세대학교, 충북대학교

### 1.2 시스템 요구사항
+	Server: Windows 98/2000/XP, Linux
+ Client: Windows 98/2000/XP, Linux
+	Memory: 64M 이상
+ Java VM: [Java 1.2.2](http://www.oracle.com/technetwork/java/javasebusiness/downloads/java-archive-downloads-javase12-419414.html)
+ Chart: JCChart
+ JDBC drivers: Oracle, MS-SQL

+ 폴더구조  
![folder tree](https://github.com/wall72/wall72.github.io/blob/master/images/xmminer_folder.png?raw=true)

+ Release: [XM-Miner v1.0](https://github.com/wall72/XM-Miner/releases/tag/1.0)
+ Release: [XM-Miner v1.5](https://github.com/wall72/XM-Miner/releases/tag/1.5)
(화장품회사 적용 버전으로 일시적으로 NICE-Miner 라고 이름변경됨)

### 1.3 빌드
+ make.bat 실행

## 2. XM-Miner 구성

### 2.1 XM-Miner Architecture
XM-Miner는 JDK1.2.2, Swing, Visibroker 4.0 에 기반하여 만들어진 100% Pure Java Application으로서 현재 Windows NT, 98, 2000 상에서 작동한다. XM-Miner의 개발된 전체적인 아키텍쳐는 다음과 같다.

![architecture](https://github.com/wall72/wall72.github.io/blob/master/images/xmminer_pic00.png?raw=true)  

### 2.2	XM-Miner 의 메뉴 구성
XM-Miner의 메뉴 구성은 File, Edit, Draw, Run, Help등의 주 메뉴와 Draw에서는 Data의 마이닝을 초기화 하기위한 Data Extractor 부분과, Data Manager부분, 마이닝 알고리즘(Algorithm)으로 구성되어 있다.

![menu tree](https://github.com/wall72/wall72.github.io/blob/master/images/xmminer_pic01.png?raw=true)  

### 2.3 XM-MIner 주요 기능
#### 1) Data Access
+	DB Access 지원 (Oracle DBMS, MS-SQL Server 지원, JDBC 드라이버와 호환되는 모든 DBMS 지원 가능)
+	파일 데이터 Access 지원 (CSV 외에 기타 구분자로 분리되는 파일)

#### 2) Data preparation
+	Sampling: 데이터 샘플링 (Random Sampling : 샘플링 숫자와 퍼센트에 따른 샘플링 제공)
+	Partitioning: 데이터 분할 (Random Sampling : 데이터 수와 퍼센트에 따른 파티셔닝 제공)
+	Transform: 데이터 변환 (Unique Value Filtering, Binary Mapping, Date 타입 변환)
+	Column Computing: 계산된 컬럼 추가 (Computed Column, Summarization Column)

#### 3)  Mining Method
+	Decision Tree ( C4.5 Algorithm을 이용)
의사결정트리는 데이터의 클러스터링(Clustering)이나 분류(Classification), 결과 예측을 위해 자주 사용되는 알고리즘이다

+	Association Rules ( Apriori Algorithm을 이용 )
데이터베이스에서 알려져 있지 않는 숨겨진 패턴을 탐사하는 연구 중에서 연관 규칙에 대해 가장 많은 연구가 이뤄졌고, 이런 연관 규칙 알고리즘에 의해 새로운 패턴을 찾아내고 있다. 연관 규칙이란 한 항목 그룹과 다른 항목 그룹 사이에 존재하는 강한 연관성을 찾아내는 마이닝 기법을 말한다. 예를 들면 앞서 잠시 언급한 미국의 편의점 체인에서 아기 기저귀를 사러 온 아빠들은 맥주팩 6개를 함께 사는 경향이 있다는 것과 같은 패턴을 찾아내는 것을 말한다.

+	Neural Network ( Backpropagation )
매우 복잡한 구조를 가진 데이터들 사이의 관계나 패턴을 찾아내는 유연한 비선형 모형(Flexible nonlinear Model)의 하나로, 신경생리학과의 유사성 때문에 일반적으로 다른(통계적) 예측모형에 비해 흥미롭게 여겨지고 있다. 이러한 신경망 모형은 고객의 신용평가, 불량거래의 색출, 의료진단예측, 우량고객의 선정, 타겟 마케팅의 여러 주제(DM, TM)등을 비롯한 여러 분야에 적용될 수가 있는데, 주로 교사학습에 적용되어 목적변수(target)에 대한 예측(Prediction)이나 분류(Classification)를 목적으로 감춰진 패턴을 찾고 이를 일반화하는데 이용된다. XM-Miner 에선 역전파(Back Propagation) 알고리즘을 사용하였다.

+	Sequence Rules ( Sequence Rule Algorithm)
트렌드를 식별해 내기 위해 일정시간 동안의 레코드를 분석하여 순서 패턴을 찾아내는 데이터 마이닝 기법으로 Association Rule의 응용의 한가지이며, 역시 Apriori 알고리즘을 사용하였다.

+	Episode Rule Learning (Episode Rule Algorithm)
시간의 순서에 따라 발생한 이벤트 리스트를 일정한 순서로 자주 발생하는 순서 패턴을 찾아내는 데이터 마이닝 기법이다.

+	Regression
데이터들의 연속적인 수가 값을 가질 경우 이러한 데이터들을 선으로 연결하여 선상에 존재하는 값으로 존재하지 않는 데이터를 예측하는 통계 기법이다.

#### 4) Output Visualization
+	Chart
데이터 마이닝 결과를 쉽고 빠른 의사 결정을 위한 다양한 차트를 제공한다.

## 3. 주요 화면

![screenshot #01](https://github.com/wall72/wall72.github.io/blob/master/images/xmminer_pic02.png?raw=true)  

![screenshot #02](https://github.com/wall72/wall72.github.io/blob/master/images/xmminer_pic03.png?raw=true)  

![screenshot #03](https://github.com/wall72/wall72.github.io/blob/master/images/xmminer_pic04.png?raw=true)  

![screenshot #04](https://github.com/wall72/wall72.github.io/blob/master/images/xmminer_pic05.png?raw=true)  

![screenshot #05](https://github.com/wall72/wall72.github.io/blob/master/images/xmminer_pic06.png?raw=true)  
