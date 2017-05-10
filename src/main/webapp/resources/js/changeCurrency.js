$(document).ready(function() {

    var toCurs= $("#toCursOutcome").val();
    var toNominal= $("#toNominalOutcome").val();
    var fromCurs = $("#fromCursOutcome").val();
    var fromNominal= $("#fromNominalOutcome").val();
    var userCur = $("#userCurOutcome").val();
    var accountCur = $("#accountCurOutcome").val();

    if (userCur !== accountCur) {
        $("#currExchangeOutcome").show();
    }

    setCursToCBCurs();

    $('#checkboxOutcome').click(function () {
        if ($(this).is(':checked')) {
            $('#customCursOutcome').prop('disabled', false);
        } else {
            setCursToCBCurs();
            $('#customCursOutcome').prop('disabled', true);
        }
    });

    $('#customCursOutcome').on('keyup change', function () {
        countAmountByCurs($('#amountOutcome').val(),$(this).val());
    });

    $('#amountOutcome').on('keyup change', function () {
        var curs = $('#customCursOutcome').val();
        countAmountByCurs($(this).val(),curs);
    });

    function countAmountByCurs(amount,customCurs) {
        var defaultAmount=(customCurs*amount).toFixed(2);
        $('#defaultAmountOutcome').val(defaultAmount);
    }

    function setCursToCBCurs() {
        var customCurs=(fromCurs/fromNominal)/(toCurs/toNominal);
        $('#customCursOutcome').val(customCurs.toFixed(4));
        countAmountByCurs($('#amountOutcome').val(),customCurs);
    }
});