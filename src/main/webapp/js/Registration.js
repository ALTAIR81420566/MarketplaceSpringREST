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
               'rePassword' : function() {
                        $('body').append('<div id="rePasswordInfo" class="info"></div>');
                        var info = $('#rePasswordInfo');
                        var ele = $('#rePassword');
                        var password = $('#password');
                        var pos = ele.offset();
                        info.css({
                            top: pos.top-3,
                            left: pos.left+ele.width()+15
                        });
                        if(ele.val() != password.val()){
                            jVal.errors = true;
                             info.removeClass('correct').addClass('error').html('← Passwords do not match').show();
                             ele.removeClass('normal').addClass('wrong');
                             return false;
                         } else {
                             info.removeClass('error').addClass('correct').html('√').show();
                             ele.removeClass('wrong').addClass('normal');
                             return true;
                         }
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
               'address' : function() {
                         $('body').append('<div id="addressInfo" class="info"></div>');
                         var addressInfo = $('#addressInfo');
                         var ele = $('#address');
                         return jVal.size(ele, addressInfo, 1);
               },
              'fullName' : function() {
                        $('body').append('<div id="nameInfo" class="info"></div>');
                        var nameInfo = $('#nameInfo');
                        var ele = $('#fullName');
                        var s = 1;
                        return   jVal.size(ele, nameInfo, 1);
               },
               'regCallback': function(user) {
                        if(user == null){
                            $('.errorText').text('User with this login already exist');
                        }else{
                            window.location.href='/authorization';
                        }
               }

        }

        $('#address').change(jVal.address);
        $('#fullName').change(jVal.fullName);
        $('#login').change(jVal.login);
        $('#password').change(jVal.password);
        $('#rePassword').change(jVal.rePassword);

         $('#register').on('click', function (e) {
            e.preventDefault();
            if(jVal.login() && jVal.password() && jVal.fullName()
                 && jVal.address() && jVal.rePassword()){
                $.post("/register", {login: $('#login').val(), password: $('#password').val(), fullName: $('#fullName').val(),
                address:  $('#address').val()},
                jVal.regCallback, 'json' )
            }
        })
});