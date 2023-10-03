# 1주차 내용 및 과제 Review
## 0.MVC 패턴
```java
package com.example.fisrtproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController { //controller 생성

    @GetMapping("/controllerTest")
    public String hello(){
        return "hello.html";
    }

    @GetMapping("/myName") //특정 url로 오는 요청을 받음
    public String name(Model model){ 
        model.addAttribute("yourName","이재현"); //model에 변수 추가
        return "index"; //view 반환
    }
}
```
**`@Controller`**

- 클래스를 컨트롤러의 역할을 하도록 만들어주는 어노테이션
<br><br>


**`public String name(Model model)`**

**`model.addAttribute("yourName","이재현");`**

- 모델 객체에 우리가 원하는 값을 변수에 대입하듯이 추가할 수 있었음
<br><br>


**`return "index"`**

- 함수는 문자열인 "index"를 반환하여, index.mustache파일을 반환함

- static 폴더에 들어있는 경우, 확장자를 명시해주어야함.

- 하지만, templates 폴더에 들어있는 view들은 확장자를 명시하지 않아도 괜찮음

<br><br>


## 1. Create 흐름 파악하기

```mustache
<form class="container" action="/articles/create" method="post">
    <div class="mb-3">
        <label class="form-label">제목</label>
        <input type="text" class="form-control" name="title">
    </div>
    <div class="mb-3">
        <label class="form-label">내용</label>
        <textarea class="form-control" rows="3" name="content"></textarea>
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
</form>
```
 **`<form ... action="/articles/create" method="post">`**

- localhost:8080/articles/create로 POST 방식 요청을 보내겠다
- 그럼 이 form 데이터는 어디로 갈까? -> 해당 url을 담당하는 컨트롤러 메소드의 매개변수 (DTO!)

```java
  @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        log.info(form.toString());
        // System.out.println(form.toString());

        // 1. DTO를 엔티티로 변환
        Article article = form.toEntity();
        log.info(article.toString());
        // System.out.println(article.toString());

        // 2. 리파지터리로 엔티티를 DB에 저장
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        // System.out.println(saved.toString());

        return "";
    }
```
**`public String createArticle(ArticleForm form)`**

- form데이터를 담기 위해 DTO만들었음!
- DTO는 Entity가 아니므로, DB에 값을 넣을 수 없음
- 따라서 Entity로 바꿔준 후, DB에 접근해야 함!!

**` Article saved = articleRepository.save(article)`**

- DB에 Entity를 저장함

```java
public interface ArticleRepository extends CrudRepository<Article, Long> {
    @Override
    ArrayList<Article> findAll(); // Iterable → ArrayList 수정
}
```
**`Spring Data JPA`**

- 위와 같은 형식으로 interface를 생성하면, 기본적인 CRUD(create, read, update, delete)와 같은 함수를 자동 생성해줌
<br><br>

## 2. Lombok refactoring

```java
public class Article {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

//@Getter, @toString만으로 아래 코드 전부 커버가능
//효율적인 Lombok 라이브러리의 어노테이션!!
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
```

**`@Getter, @toString ,...`**

- 우리가 이제껏 기본적으로 만들던 함수들을 제공
- 코드를 간결하게 하고, 편리하게 사용할 수 있다!! 잘 쓰자

**`@Slf4j`**

- println() 대신에, 로그를 남기는 방식
- 왜 로그를 남겨야 하는거죠 ? -> https://wildeveloperetrain.tistory.com/204
- 빠르고 정해진 포맷으로 로깅이 가능하며, 에러에 관한 정보를 많이 제공한다...
<br><br>


## 3. Read 흐름 파악하기
```java
@GetMapping("/articles/{id}") // 데이터 조회 요청 접수
    public String show(@PathVariable Long id, Model model) { // 매개변수로 id 받아오기
        log.info("id = " + id); // id를 잘 받았는지 확인하는 로그 찍기

        // 1. id를 조회하여 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 2. 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);

        // 3. 뷰 페이지 반환하기
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {
        // 1. 모든 데이터 가져오기
        List<Article> articleEntityList = articleRepository.findAll();

        // 2. 모델에 데이터 등록하기
        model.addAttribute("articleList", articleEntityList);

        // 3. 뷰 페이지 설정하기
        return "articles/index";
    }
```
**`@PathVariable`**

- url에 특정 값을 받아올 수 있음

**`articleRepository.findById(id) or articleRepository.findByAll()`**

**`model.addAttribute("articleList", articleEntityList);`**

**`return "articles/index";`**

- DB에서 데이터를 가져오고! (articleRepository.findById(id)로 가져온 Article객체를 수정하면 DB는 어떻게 될까)
- 데이터를 Model에 붙이고
- view를 반환한다!

