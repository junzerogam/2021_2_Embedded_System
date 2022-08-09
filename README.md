# Embedded System Project
* Raspberry PI & Coap Protocol 기반 Outback Table Status GUI Project (2021.10-2021.12)  
* 2021 Fall Season Embeded System Project

## ❗ Purpose
1. Raspberry Pi 기반 임베디드 시스템 기초 쌓기
2. Coap Protocol Observe Option 활용 
3. 다양한 센서를 활용하여 하드웨어 지식 배양
4. 실생활에 접목시킬 수 있는 아이디어 생산
## 💡 Summary
> 현재 Outback Steakhouse 테이블 안내 방식은 무전기로 인한 소통을 주로 활용하고 있다. 무전기는 단방향 통신을 주로 사용하고 있어, 상호 소통의 한계를 가지고 있다. 
> 더하여 사람이 지속적으로 꾸준히 관찰 및 움직여야 하므로 체력 소모가 발생한다.<br><br>
> 이를 해결하기 위해 라즈베리파이와 PIR 센서를 활용하여 다음과 같은 효과를 기대한다.
> * GUI표현으로 소통 및 직관력 증대
> * 인력 감소
> * 효율성 극대화
## 📜 Structure
<p align="center">
  <img src=docs/img/structure.png width="70%" height="70%">
</p>  

## 🏃 Ongoing
### :one: Schemetic & Setting
<p align="left">
  <img src=docs/img/회로도.png width="30%" height="30%">
  <img src=docs/img/테이블.jpg width="30%" height="30%">
</p>  
                                                
<br><br>  
### :two: Connect Raspberry Pi Wireless mode & Implement Java jar file
<p align="left">
  <img src=docs/img/Connect.png width="70%" height="70%">
</p>  

```
pi@raspberrypi:~ $ sudo java -jar project_pirserver.jar
```
### :three: Start Get

<p align="left">
  <img src=docs/gif/start.gif width="70%" height="70%">
</p> 

<img src=docs/img/starteat.png width="50%" height="50%">  

### :four: Option : Put

<p align="left">
  <img src=docs/gif/put.gif width="70%" height="70%">
</p> 

### :five: Option : Observe

<p align="left">
  <img src=docs/gif/observe.gif width="70%" height="70%">
</p> 

<img src=docs/img/eatnow2.png width="50%" height="50%">  
<img src=docs/img/finisheat.png width="50%" height="50%">

## 📌 Result
✔️ Coap Protocol의 전반적인 이해  
✔️ Raspberry PI의 구조와 다양한 센서 활용 경험  
✔️ 리눅스 활용 방법 터득  
✔️ 아웃백에 아이디어 공유 😄  
## 😄 What's Next?
✔️ 나우웨이팅 API & 포스기 연동  
✔️ 웹페이지 구현으로 프로젝트 확장
