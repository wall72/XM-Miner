@echo off

SET XM_MINER=C:\XM-Miner
SET JAVA_HOME=%XM_MINER%\jdk1.2.2

SET PATH=%JAVA_HOME%\bin;%PATH%
SET CLASSPATH=.;%XM_MINER%\jcchart\jcchart.jar

echo [XMServer compiling...]

javac xmminer\xmserver\xmlib\*.java
javac xmminer\xmserver\xmgraph\*.java

javac xmminer\xmserver\xmgraph\xmdnode\xmdnodesampling\*.java
javac xmminer\xmserver\xmgraph\xmdnode\xmdnodepartitioning\*.java
javac xmminer\xmserver\xmgraph\xmdnode\xmdnodecolumnselect\*.java
javac xmminer\xmserver\xmgraph\xmdnode\xmdnodecalculate\*.java
javac xmminer\xmserver\xmgraph\xmdnode\xmdnodedataquery\*.java
javac xmminer\xmserver\xmgraph\xmdnode\xmdatamanage\*.java
javac xmminer\xmserver\xmgraph\xmdnode\xmredundantcheck\*.java

javac xmminer\xmserver\xmgraph\xmxnode\xmxnodedb\*.java
javac xmminer\xmserver\xmgraph\xmxnode\xmxnodefile\*.java
javac xmminer\xmserver\xmgraph\xmxnode\xmxnodefile\filex\*.java
javac xmminer\xmserver\xmgraph\xmxnode\xmxnodefile\xmfileio\*.java
javac xmminer\xmserver\xmgraph\xmxnode\xmxnodefile\xmutil\*.java

javac xmminer\xmserver\xmgraph\xmmnode\xmmnoderegression\*.java
javac xmminer\xmserver\xmgraph\xmmnode\xmmnodeassociationrule\*.java
javac xmminer\xmserver\xmgraph\xmmnode\xmmnodesequencerule\*.java
javac xmminer\xmserver\xmgraph\xmmnode\xmmnodec45\*.java
javac xmminer\xmserver\xmgraph\xmmnode\xmmnodec45\rulegen\*.java
javac xmminer\xmserver\xmgraph\xmmnode\xmmnodec45\rulegen\typedef\*.java
javac xmminer\xmserver\xmgraph\xmmnode\xmmnodec45\treegen\*.java
javac xmminer\xmserver\xmgraph\xmmnode\xmmnodeneuralnetwork\*.java

echo [XMClient compiling...]

javac xmminer\xmclient\*.java
javac xmminer\xmclient\xmlib\*.java
javac xmminer\xmclient\xmgraph\*.java

javac xmminer\xmclient\xmgraph\xmdnode\xmdnodesampling\*.java
javac xmminer\xmclient\xmgraph\xmdnode\xmdnodepartitioning\*.java
javac xmminer\xmclient\xmgraph\xmdnode\xmdnodecolumnselect\*.java
javac xmminer\xmclient\xmgraph\xmdnode\xmdnodecalculate\*.java
javac xmminer\xmclient\xmgraph\xmdnode\xmdnodedataquery\*.java
javac xmminer\xmclient\xmgraph\xmdnode\xmdnodetablemodel\*.java

javac xmminer\xmclient\xmgraph\xmxnode\xmxnodedb\*.java
javac xmminer\xmclient\xmgraph\xmxnode\xmxnodefile\*.java
javac xmminer\xmclient\xmgraph\xmxnode\xmxnodefile\corbautil\*.java
javac xmminer\xmclient\xmgraph\xmxnode\xmxnodefile\filexscr\*.java
javac xmminer\xmclient\xmgraph\xmxnode\xmxnodefile\filextable\*.java
javac xmminer\xmclient\xmgraph\xmxnode\xmxnodefile\filextree\*.java
javac xmminer\xmclient\xmgraph\xmxnode\xmxnodefile\xmscrutil\*.java

javac xmminer\xmclient\xmgraph\xmmnode\xmmnoderegression\*.java
javac xmminer\xmclient\xmgraph\xmmnode\xmmnodeassociationrule\*.java
javac xmminer\xmclient\xmgraph\xmmnode\xmmnodesequencerule\*.java
javac xmminer\xmclient\xmgraph\xmmnode\xmmnodec45\*.java
javac xmminer\xmclient\xmgraph\xmmnode\xmmnodeneuralnetwork\*.java