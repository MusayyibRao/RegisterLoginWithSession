package com.registerlogin.controller;

import com.registerlogin.dao.RegisterRepository;
import com.registerlogin.model.Register;
import com.registerlogin.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/register")
public class RegisterController {


@Autowired
    private BCryptPasswordEncoder encoder;

@Autowired
    private RegisterService regSer;

@Autowired
    RegisterRepository regRepo;

@PostMapping("/add_user")
    public Register addRegister(@RequestBody Register register)
   {
       Register user=regRepo.findByUsername(register.getUsername());
       System.out.println(register);
       if(user!=null)
       {
           return user;
       }

       else {
           String password=register.getPassword();
           String encode=encoder.encode(password);
           register.setPassword(encode);
           String confirmPass=register.getConfirmPassword();
           String encode1=encoder.encode(confirmPass);
           register.setConfirmPassword(encode1);

   }

       System.out.println(register);
       Register register1=regSer.addUsers(register);
       return register1;

   }


@GetMapping("/{id}")
    public Register getData(@PathVariable("id") long id)
{
    return  regSer.getUsers(id);
}


@DeleteMapping("/{id}")
    public String deleteData(@PathVariable("id") long id)
{

    regSer.deleteUsers(id);
return "Delete Data Successfully......";
}

@PutMapping("/{id}")
    public Register updateData(@RequestBody Register register,@PathVariable("id") long id)
{
   register.setId(id);
   String password=register.getPassword();
   String encode=encoder.encode(password);
   register.setPassword(encode);
   String confirmPassword=register.getConfirmPassword();
   String encode1=encoder.encode(confirmPassword);
   register.setConfirmPassword(encode1);

  Register register1=regSer.updateProducts(register);
return register1;
}


@PostMapping("/destroy")
public String destroySession(HttpServletRequest request)
{
    request.getSession().invalidate();
    return "users Logout Successfully";

}

@GetMapping("/home")
    public String process(Model model, HttpSession session)
{

    String message=session.getAttribute("session").toString();
  model.addAttribute("session",message);
  return "welcome User--";

}

@PostMapping("/login")
    public String persertUser(@RequestBody Register register, String msg, HttpServletRequest request, HttpServletResponse response)
{
  String encode=register.getPassword();
  Register register1=regRepo.findByUsername(register.getUsername());
  System.out.println("encoder password"+register1.getPassword());
  boolean isPasswordMatch=encoder.matches(encode,register1.getPassword());

  if(isPasswordMatch)
  {
      System.out.println(isPasswordMatch+"UserLogin Sucessfully..");

  }

  else {

      return "invalid Password...";
  }

  @SuppressWarnings("unchecked")
  List<String> message= (List<String>) request.getSession().getAttribute("session");
if(message==null)
{
    message=new ArrayList<>();
    HttpSession session=request.getSession();
    session.setMaxInactiveInterval(60);
    session.invalidate();
}
message.add(msg);
request.getSession().setAttribute("session",message);
return "User login successfully";

}

}
