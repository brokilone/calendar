$(function () {
    $('#changePass').on('click', function () {
        $('#passChange').show();
        $(this).hide();
        $('p.text-danger').hide();

    });
    $('#noChangePass').on('click', function () {
        $('#passChange').show();
        $('#changePass').show();
    });

})