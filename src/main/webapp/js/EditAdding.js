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
               'title' : function() {
                        $('body').append('<div id="titleInfo" class="info"></div>');
                        var titleInfo = $('#titleInfo');
                        var ele = $('#title');
                        var s = 1;
                        return jVal.size(ele, titleInfo, 1);
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
                          return  jVal.number(ele, info);
                        }else{
                            return true;
                        }

               },
               'addCallback' : function(data) {
                    $('#response').text(data.resp);
               }

        };

        $('#title').change(jVal.title);
        $('#startPrice').change(jVal.startPrice);
        $('#timeLeft').change(jVal.timeLeft);
        $('#register').click(jVal.registerBtn);
        $('#publishBtn').on('click', function (e) {
                    e.preventDefault();
                    if(jVal.title() && jVal.startPrice() && jVal.timeLeft()){
                        var buyNow = false;
                        if($("#buyItNowCheckBox").is(':checked')) {
                            buyNow = true;
                        }
                           $.post("/add", {
                                   title: $('#title').val(),
                                   startPrice: $('#startPrice').val(),
                                   timeLeft: $('#timeLeft').val(),
                                   description: $('#description').val(),
                                   step: $('#step').val(),
                                   buyItNow: buyNow
                           }, jVal.addCallback, 'json' )
                    }
        });
});