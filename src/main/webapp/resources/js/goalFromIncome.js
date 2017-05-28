$(document).ready(function () {
    $('.input-group-addon').find('.checkboxGoal').click(function () {
        $('.goals').each(function () {
            $(this).toggle('slow');
        })
    });

    $('.radio-button').click(function () {
        $('form').find('.amountGoal').prop('disabled', true);
        var button = $(this);
        if (button.hasClass('percentToGoal') && button.is(':checked')) {
            $('form').find('.percents').prop('disabled', false);
            $('form').find('.amountGoal').prop('disabled', true);
        }
        if (button.hasClass('amountToGoal') && button.is(':checked')) {
            $('form').find('.percents').prop('disabled', true);
            $('form').find('.amountGoal').prop('disabled', false);
        }
    });

});

