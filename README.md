## 프로젝트 실행 방법 

실행환경 : Java 21, Gradle 설치 

### 1. 레포지토리 클론
```commandline
git clone https://github.com/sangyun0904/CodeJuniorBackendEngineer.git
```
### 2. 프로젝트 경로로 이동 
```commandline
cd CodeJuniorBackendEngineer
```
### 3. 프로젝트 실행 
```commandline
./gradlew bootRun
```
## API 사용법 
### 저자(Author) API
생성:
POST localhost:8080/authors  
요청 본문 예시:  
{  
  &nbsp;&nbsp;&nbsp;&nbsp;"name": "홍길동",  
  &nbsp;&nbsp;&nbsp;&nbsp;"email": "hong@example.com"  
}  

목록 조회:  
GET localhost:8080/authors  

상세 조회:  
GET localhost:8080/authors/{id}  

수정:  
PUT localhost:8080/authors/{id}  

삭제:  
DELETE localhost:8080/authors/{id}

### 도서(Book) API
생성:  
POST localhost:8080/books
요청 본문 예시:  
{  
&nbsp;&nbsp;&nbsp;&nbsp;"title": "예제 도서",  
&nbsp;&nbsp;&nbsp;&nbsp;"description": "도서에 대한 설명",  
&nbsp;&nbsp;&nbsp;&nbsp;"isbn": "8811112220",  
&nbsp;&nbsp;&nbsp;&nbsp;"publication_date": "2025-01-01",  
&nbsp;&nbsp;&nbsp;&nbsp;"author_id": 1  
}  

목록 조회:  
GET localhost:8080/books

상세 조회:  
GET localhost:8080/books/{id}  

수정:  
PUT localhost:8080/books/{id}  

삭제:  
DELETE localhost:8080/books/{id}  

## Swagger 문서 접근 
http://localhost:8080/swagger-ui/index.html

## 기타 주의 사항
8080 포트가 현재 사용중은 아닌지 확인해주세요!