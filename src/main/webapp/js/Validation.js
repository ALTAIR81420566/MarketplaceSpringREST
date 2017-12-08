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
             'notUserName' : function(ele, info){
                  var pos = ele.offset();
                  var name = $('#fullName');
                  info.css({
                    top: pos.top-3,
                    left: pos.left+ele.width()+15
                  });
                  if(ele.val() == name.val()){
                  jVal.errors = true;
                  info.removeClass('correct').addClass('error').html('← must not be the same as the full name').show();
                  ele.removeClass('normal').addClass('wrong');
                   return false;
                   }else {
                    info.removeClass('error').addClass('correct').html('√').show();
                    ele.removeClass('wrong').addClass('normal');
                    return true;
                   }

             },
             'number' : function(ele, info){
                    var pos = ele.offset();
                    info.css({
                    top: pos.top-3,
                    left: pos.left+ele.width()+15
                    });

                    if(parseInt(ele.val()) != ele.val()) {
                        jVal.errors = true;
                        info.removeClass('correct').addClass('error').html('← it must be a number').show();
                        ele.removeClass('normal').addClass('wrong');
                        return false;
                    } else {
                        info.removeClass('error').addClass('correct').html('√').show();
                        ele.removeClass('wrong').addClass('normal');
                            return true;
                    }
             },
             'time' : function(ele, info){
                    var pos = ele.offset();
                      info.css({
                         top: pos.top-3,
                         left: pos.left+ele.width()+15
                      });
                      var patt = /^00\:(([1-6][0-9])|([0-6][1-9]))/i;
                      var patt2 = /^([1-9][0-9])|([0-9][1-9])\:[0-6][0-9]/i;
                      if(!patt.test(ele.val()) && !patt2.test(ele.val())) {
                      jVal.errors = true;
                      info.removeClass('correct').addClass('error').html('← time should be in format HH:MI').show();
                      ele.removeClass('normal').addClass('wrong');
                      return false;
                      } else {
                        info.removeClass('error').addClass('correct').html('√').show();
                        ele.removeClass('wrong').addClass('normal');
                        return true;
                      }
                                       },
              'fullName' : function() {
                        $('body').append('<div id="nameInfo" class="info"></div>');
                        var nameInfo = $('#nameInfo');
                        var ele = $('#fullName');
                        var s = 1;
                        return   jVal.size(ele, nameInfo, 1);
               },
               'title' : function() {
                        $('body').append('<div id="titleInfo" class="info"></div>');
                        var titleInfo = $('#titleInfo');
                        var ele = $('#title');
                        var s = 1;
                        return jVal.size(ele, titleInfo, 1);
               },
               'address' : function() {
                         $('body').append('<div id="addressInfo" class="info"></div>');
                         var addressInfo = $('#addressInfo');
                         var ele = $('#address');
                         return jVal.size(ele, addressInfo, 1);
               },
               'password' : function() {
                         $('body').append('<div id="passwordInfo" class="info"></div>');
                         var passwordInfo = $('#passwordInfo');
                         var ele = $('#password');
                         return jVal.size(ele, passwordInfo, 6);
               },
               'startPrice' : function() {
                        $('body').append('<div id="startPriceInfo" class="info"></div>');
                        var info = $('#startPriceInfo');
                        var ele = $('#startPrice');
                        return jVal.number(ele, info);
               },
               'timeLeft' : function() {
                        $('body').append('<div id="timeLeftInfo" class="info"></div>');
                        var info = $('#timeLeftInfo');
                        var ele = $('#timeLeft');
                        if(!$("#buyItNowCheckBox").is(':checked')) {
                          return  jVal.time(ele, info);
                        }else{
                            return true;
                        }

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
                             nfo.removeClass('correct').addClass('error').html('← Passwords do not match').show();
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
               'registerBtn' : function() {
                    if(jVal.login() && jVal.password() && jVal.fullName()
                        && jVal.address() && jVal.rePassword()){
                        window.location.href='General.html';
                    }
               },
               'addBtn' : function() {
                   window.location.href='Adding.html';
               },
               'publishBtn' : function() {
                    if(jVal.title() && jVal.startPrice() && jVal.timeLeft()){
                       window.location.href='General.html';
                    }
               }
        };
        $('#address').change(jVal.address);
        $('#fullName').change(jVal.fullName);
        $('#login').change(jVal.login);
        $('#password').change(jVal.password);
        $('#rePassword').change(jVal.rePassword);
        $('#title').change(jVal.title);
        $('#startPrice').change(jVal.startPrice);
        $('#timeLeft').change(jVal.timeLeft);
        $('#register').click(jVal.registerBtn);
        $('#addBtn').click(jVal.addBtn);
        $('#publishBtn').click(jVal.publishBtn);


         $('#register').on('click', function (e) {
            e.preventDefault();
            if(jVal.login() && jVal.password() && jVal.fullName()
                 && jVal.address() && jVal.rePassword()){
                window.location.href='General.html';
            }
        })
});