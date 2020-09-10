$(function () {
    $('.resetButton').on('click', function () {
        $(this).hide();
        $(this).closest('td').children('.abortButton').show();
    });

    $('.abortButton').on('click', function () {
        $(this).hide();
        $(this).closest('td').children('.update-data').show();
        $(this).closest('td').children('.resetButton').hide();
        $(this).closest('td').children('.delete-data').show();
        $(this).closest('td').children('.submitButton').hide();
        $(this).parents('tr').find('input').prop('disabled', true);

    })
})