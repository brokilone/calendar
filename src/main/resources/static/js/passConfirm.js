$(function () {
    $('form.pass-header').on('submit', function (e) {

        if ($('#pass').val() != $('#matchPass').val()) {
            console.log("err");
            e.preventDefault();
            $('#error-pass').show();
        }
    });

    $('#pass').on('focus', function () {
        $('p.text-danger').hide();
        $('#error-pass').hide();
    })

    $('form#passChange').on('submit', function (e) {

        if ($('#pass').val() != $('#passConfirm').val()) {
            console.log("err");
            e.preventDefault();
            $('#error-pass').show();
        }
    });

    $('#oldpass').on('focus', function () {
        $('#error-pass').hide();
    })

})