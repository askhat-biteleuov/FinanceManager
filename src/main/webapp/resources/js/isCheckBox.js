$(document).ready(function() {
    $('input[name=checkbox]').click(function () {
        if ($(this).is(':checked')) {
            $('input[name=currency]').prop('disabled', false);
        } else {
            $('input[name=currency]').prop('disabled', true);

        }
    });
    $('input[name=currency]').on('keyup change', function () {
        $('label[name=defaultAmmount]').text($(this).val());
    });
});