$(function () {
    $('form.pass-header').on('submit', function (e) {

        if ($('#pass').val() != $('#matchPass').val()) {
            console.log("err");
            e.preventDefault();
            $('#error-pass').show();
        }
    });

})