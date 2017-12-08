
$(document).ready(function(){
        jVal = {
               'size' : function(ele, info, s){
                        var pos = ele.offset();
                        info.css({
                            top: pos.top-3,
                            left: pos.left+ele.width()+15
                        });
                         if(ele.val().length < s) {
                           jVal.errors = true;
                           info.removeClass('correct').addClass('error').html('← at least '+ s +' characters').show();
                           ele.removeClass('normal').addClass('wrong');
                           return false;
                         } else {
                                info.removeClass('error').addClass('correct').html('√').show();
                                ele.removeClass('wrong').addClass('normal');
                                return true;
                         }
               },
               'password' : function() {
                         $('body').append('<div id="passwordInfo" class="info"></div>');
                         var passwordInfo = $('#passwordInfo');
                         var ele = $('#password');
                         return jVal.size(ele, passwordInfo, 6);
               },
               'login' : function() {
                          $('body').append('<div id="loginInfo" class="info"></div>');
                          var info = $('#loginInfo');
                          var ele = $('#login');
                          var name = $('#fullName');
                          var pos = ele.offset();
                          info.css({
                              top: pos.top-3,
                              left: pos.left+ele.width()+15
                          });
                          if(ele.val().length < 6) {
                              jVal.errors = true;
                              info.removeClass('correct').addClass('error').html('← at least 6 characters').show();
                              ele.removeClass('normal').addClass('wrong');
                              return false;
                          } else if(ele.val() == name.val()){
                              jVal.errors = true;
                              info.removeClass('correct').addClass('error').html('← must not be the same as the full name').show();
                              ele.removeClass('normal').addClass('wrong');
                              return false;
                          } else {
                              info.removeClass('error').addClass('correct').html('√').show();
                              ele.removeClass('wrong').addClass('normal');
                              return true;
                          }
               },
               'authorizationCallback': function(data) {
                $('#errorText').text("error");
                  if(data.resp == "success"){
                    window.location.href='/general';
                  }else{
                    $('.errorText').text(data.resp);
                  }
               }
        };
         $('#login').change(jVal.login);
         $('#password').change(jVal.password);
         $('#signIn').on('click', function (e) {
            e.preventDefault();
            if(jVal.login() && jVal.password()){
               $.post("/authorization", {login: $('#login').val(), password: $('#password').val()}, jVal.authorizationCallback, 'json' )
            }
         });
        $('#entAsGuest').on('click', function (e) {
            e.preventDefault();
            $.post("/authorization", {role: 'guest'});
            window.location.href='/general';
        });
        $('#onRegisterPage').on('click', function (e) {
            e.preventDefault();
            window.location.href='/register';
        });
});

