$(function () {
        $('.update-data').on('click', function () {
            $(this).parents('table').find('input').prop('disabled', true);
            $(this).parents('table').find('.submitButton').hide();
            $(this).parents('table').find('.resetButton').hide();
            $(this).parents('table').find('.delete-data').show();
            $(this).parents('table').find('.update-data').show();
            $(this).parents('table').find('.abortButton').hide();
            $(this).parents('table').find('.delete-file').hide();
            $(this).parents('table').find('.add-file').hide();
            $(this).closest('td').children('.submitButton').show();
            $(this).closest('td').children('.resetButton').show();
            $(this).closest('td').children('.delete-data').hide();
            if($(this).parents('tr').find('.delete-file').find(":checkbox").is(':checked')){
                $(this).parents('tr').find('.delete-file').show();
            } else {
                $(this).parents('tr').find('.add-file').show();
            }
            $(this).parents('tr').find('input').prop('disabled', false);
            $(this).hide();
        });

    $('.delete-data').on('click', function () {
        $(this).find(":checkbox").prop('checked', false);
        $(this).parents('tr').css('display', 'none');
        $(this).closest('td').children('.submitButton').trigger('click');
    });

    $('#addButton').on('click', function () {
        $('#addTask').show();

    });

    $('.delete-file').on('click', function () {
        $(this).find(":checkbox").prop('checked', false);
        $(this).parents('tr').find('.add-file').show();
        $(this).parents('td').find('a').hide();
        $(this).hide();
    });

    $('#changeData').on('click', function () {
        $('#userChange').show();
        $(this).hide();
        $('#userDataTable').hide();

    });
    $('#noChange').on('click', function () {
        $('#userChange').hide();
        $('#changeData').show();
        $('#userDataTable').show();
    });

})