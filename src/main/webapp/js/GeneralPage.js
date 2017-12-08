
$(document).ready(function(){
        jGeneral = {
        };
         $('#myProd').on('click', function (e) {
            e.preventDefault();
            window.location.href='/myProducts';
         });
         $('#backBtn').on('click', function (e) {
            e.preventDefault();
            window.location.href='/general';
         });
});
