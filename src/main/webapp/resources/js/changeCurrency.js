$(document).ready(function() {
    var toCurs= $("#toCurs").val();
    var toNominal= $("#toNominal").val();
    var fromCurs = $("#fromCurs").val();
    var fromNominal= $("#fromNominal").val();

    setCursToCBCurs();

    $('input[name=checkbox]').click(function () {
        if ($(this).is(':checked')) {
            $('input[name=customCurs]').prop('disabled', false);
        } else {
            setCursToCBCurs();
            $('input[name=customCurs]').prop('disabled', true);
        }
    });

    $('input[name=customCurs]').on('keyup change', function () {
        countAmountByCurs($('#outcomeAmount').val(),$(this).val());
    });

    $('#outcomeAmount').on('keyup change', function () {
        var curs = $('input[name=customCurs]').val();
        countAmountByCurs($(this).val(),curs);
    });

    function countAmountByCurs(amount,customCurs) {
        var defaultAmount=(customCurs*amount).toFixed(2);
        $('input[name=defaultAmount]').val(defaultAmount);
    }

    function setCursToCBCurs() {
        var customCurs=(fromCurs/fromNominal)/(toCurs/toNominal);
        $('input[name=customCurs]').val(customCurs.toFixed(4));
        countAmountByCurs($('#outcomeAmount').val(),customCurs);
    }
});