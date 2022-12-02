package red.com.pwh.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import red.com.pwh.exeption.LocationNotFoundException;
import red.com.pwh.exeption.TerroristStateFoundException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(LocationNotFoundException.class)
    public String locationNotFound(LocationNotFoundException exception, Model model){
        model.addAttribute("err_code","404");
        model.addAttribute("error",exception.getMessage());
        model.addAttribute("pic","error.png");
        return "error";
    }

    @ExceptionHandler(TerroristStateFoundException.class)
    public String russiaIsFound(TerroristStateFoundException exception, Model model){
        model.addAttribute("err_code","404");
        model.addAttribute("error",exception.getMessage());
        model.addAttribute("pic","err.png");
        return "error";
    }
}
