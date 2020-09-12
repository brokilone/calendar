$(function () {
    $('.resetButton').on('click', function () {
        $(this).hide();
        $(this).closest('td').children('.abortButton').show();
        $(this).parents('tr').find('.add-file').hide();
        if ($(this).parents('tr').find('.delete-file').find(":checkbox").is(':checked')) {
            $(this).parents('tr').find('.delete-file').show();
        }
        $(this).parents('tr').find('a').show();
    });

    $('.abortButton').on('click', function () {
        $(this).hide();
        $(this).closest('td').children('.update-data').show();
        $(this).closest('td').children('.resetButton').hide();
        $(this).closest('td').children('.delete-data').show();
        $(this).closest('td').children('.submitButton').hide();
        $(this).parents('tr').find('input').prop('disabled', true);
        $(this).parents('tr').find('.delete-file').hide();
        $(this).parents('tr').find('.add-file').hide();
    })
})