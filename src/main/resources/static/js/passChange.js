$(function () {
    $('#changePass').on('click', function () {
        $('#passChange').show();
        $(this).hide();
        $('p.text-danger').hide();
        $('#userDataTable').hide();
        $('#changeData').hide();
        $('#changeTheme').hide();

    });
    $('#noChangePass').on('click', function () {
        $('#passChange').hide();
        $('#changePass').show();
        $('#userDataTable').show();
        $('#changeData').show();
        $('#changeTheme').show();
    });

})