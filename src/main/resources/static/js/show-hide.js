$(function () {
        $('.update-data').on('click', function () {
            $(this).parents('table').find('input').prop('disabled', true);
            $(this).parents('table').find('.submitButton').hide();
            $(this).parents('table').find('.resetButton').hide();
            $(this).parents('table').find('.delete-data').show();
            $(this).parents('table').find('.update-data').show();
            $(this).parents('table').find('.abortButton').hide();
            $(this).closest('td').children('.submitButton').show();
            $(this).closest('td').children('.resetButton').show();
            $(this).closest('td').children('.delete-data').hide();
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

    })

})