
### Project 배포 파일 생성 방법
Project Browser에서 프로젝트를 선택한 후 마우스 오른쪽 버튼 클릭

[Export]-[Java]-[Runnable JAR file] 선택

![004.png](./readme_image/004.png)

Launch configuration을 WIZnet_Configuration_Tool로 선택하고 생성될 설치파일이 저장될 경로를 설정 함

![005.png](./readme_image/005.png)


### Project 'configuration_tool' is missing required library Error시 해결 방법

본 프로젝트를 Import 한 후, Build 하면 아래와 같은 에러가 발생 할 수 있다.

![002.png](./readme_image/002.png)

해당 에러는 Eclipse에서 사용하는 Libary Path가 절대 경로로 되어 있어서 발생한 문제로 판단되며 아래 방법으로 해결 가능 하다.
==(추후 보완이 필요)==

1. x표시 되어 있는 library를 선택하고 Edit 버튼을 클릭
2. 개발 PC의 Eclipse 설치 경로\plugins 폴더에서 해당 library를 선택 ( 버전은 약간 다를 수 있음 )

![001.png](./readme_image/001.png)

![003.png](./readme_image/003.png)

### java GUI designer plugin 설치 방법

## Java용 GUI설계 툴 Window Builder 설치하기 ##

사용하는 Tool은 Java를 기반으로 동작하는 Tool로서 Java용 GUI 설계를 도와주는 Widow Buildrer가 필요하다.
Window Builder는 WYSIWYG(What You See Is What You Get)툴이다.

1. [www.eclipse.org/windowbuilder](http://www.eclipse.org/windowbuilder/ "http://www.eclipse.org/windowbuilder/") 접속
2. 아래 표시된 부분을 클릭하여 다운로드 페이지 이동


![](http://i.imgur.com/m4cN1PL.png)

3.아래 네모 표시한 부분에서 자신의 Eclipse 버전에 맞는 다운로드 링크(Update Site 하단)를 클릭한다.(네모 표시의 오른편에 Integration Version은 정식 릴리즈 버전이 아니므로 불안정 할 수 있다.)

![](http://i.imgur.com/z9kozjJ.png)

4.링크를 클릭하면 아래와 같은 페이지가 나타난다. (여기서는 "4.3(Kelper)"용 링크를 클릭하였다.) 
> 이 페이지는 아래에서 다시 참조해야 하므로 닫지 않는다.

![](http://i.imgur.com/TUs91jz.png)

5.이 페이지의 설명대로 따라하면 Plug-in이 설치 되는데 어떠한 과정을 거치는지 살펴보자.
6.Eclipse를 실행하고 아래 표시한 바와 같이 메뉴바에서 "Help -> Install New Software.." 클릭한다.

![](http://i.imgur.com/IJpRRWw.png)

7.새로 나타나는 창에서 "add..."(아래 화실표시) 버튼을 클릭하면 "Add Repository"창이 나타난다.

![](http://i.imgur.com/QPeAixf.png)

8."Add Repository"창에서 "Name"에는 적당한 이름을 기입하고, "Location"에는 4에서 접속한 페이지의 주소를 복사해서 붙여 넣는다.(아래 그림에서 네모 표시 참조)

![](http://i.imgur.com/Iu2DwKN.png)

9."OK"버튼을 누르면 다음과 같은 내용이 표시된다. 여기서 아래 화살표시 된대로 "Select All" 버튼을 누르고, "Next >"버튼을 누른다.

![](http://i.imgur.com/thXbEph.png)

10.설치될 내용을 표시해준다. 확인하고 "Next >" 클릭.

![](http://i.imgur.com/N8Xom2z.png)

11.아래와 같이 "License"를 확인하는 창이 나타난다. 동의하기 위해 네모 표시를 선택하고 "Finish" 버튼을 누른다.

![](http://i.imgur.com/tPyydwO.png)

12.설치가 진행된다.

![](http://i.imgur.com/90y6bqg.png)

13.설치를 마치면 아래와 같이 Eclipse를 재시작할 것을 요구한다. "Yes"버튼을 눌러 Eclipse를 재시작한다.

![](http://i.imgur.com/7kMKCIj.png)

14.이로써 Window Builder 설치를 모두 마쳤다. 하지만 Eclipse를 재시작하고 나면 아무런 내용이 표시되지 않는다. 당황하지 않고 아래 화살표로 표시한 부분 2군데를 클릭해준다. (1. 상단 메뉴바 -> Window -> Show Toolbar, 2. 오른쪽 상단 윈도우 최대화 버튼)

![](http://i.imgur.com/tElbpM7.png)
