package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleUpdateForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BootCampController {
    @Autowired
    ArticleRepository articleRepository;

    //<a href="articles/{{article.id}}/edit">Edit</a> 를 통해서 여기서 GetMapping에 잡힘
    //이렇게 두 번을 거쳐서 edit.mustache로 보내는 이유 : model객체를 사용하기 위함
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){

        //수정하기 전 데이터를 가져오기 위해서, id를 통해서 target을 가져옴
        Article target=articleRepository.findById(id).orElse(null);

        //target의 내용을 model객체에 추가
        model.addAttribute("article",target);

        //view 반환
        return "articles/edit";
    }

    //dto 생성
    @PostMapping("/articles/update")
    public String update(ArticleUpdateForm dto){
        //아래와 같은 방식은 post와 동일함 -> update가 아님
//        Article article = dto.toEntity();
//        articleRepository.save(article);
//        return "redirect:/articles/"+article.getId();

        //dto에서 id를 가져옴
        long id = dto.getId();

        //id에 해당하는 DB에 존재하는 값을 가져옴 (여기에 덮어씌울 예정)
        Article target = articleRepository.findById(id).orElse(null);

        //해당 값에 dto값을 update
        target.update(dto);

        //수정된 값을 다시 db로 저장.
        articleRepository.save(target);

        //redirect로 수정 후 결과 바로 확인 가능
        return "redirect:/articles/"+target.getId();
    }


    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id){

        //Delete는 변경사항이나 DB에 저장할 필요가 없다.
        //그냥 찾아서 삭제하고 redirect 해주면 된다.

        //id로 DB에서 값 꺼내오기
        Article target=articleRepository.findById(id).orElse(null);

        //해당 값과 동일한 값 DB에서 삭제
        articleRepository.delete(target);

        //redirect
        return "redirect:/articles";
    }

}
