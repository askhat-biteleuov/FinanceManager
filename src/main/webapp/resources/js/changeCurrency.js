$(document).ready(function() {

    $('.outBtn').on('click', function () {
        var closestDiv = $(this).closest('div');
        var toCurs= closestDiv.find(".toCursOutcome").val();
        var toNominal= closestDiv.find(".toNominalOutcome").val();
        var fromCurs = closestDiv.find(".fromCursOutcome").val();
        var fromNominal= closestDiv.find(".fromNominalOutcome").val();
        var userCur = closestDiv.find(".userCurOutcome").val();
        var accountCur = closestDiv.find(".accountCurOutcome").val();

        if (userCur !== accountCur) {
            closestDiv.find(".currExchangeOutcome").show();
        }

        setCursToCBCurs();

        closestDiv.find('.checkboxOutcome').click(function () {
            if ($(this).is(':checked')) {
                closestDiv.find('.customCursOutcome').prop('disabled', false);
            } else {
                setCursToCBCurs();
                closestDiv.find('.customCursOutcome').prop('disabled', true);
            }
        });

        closestDiv.find('.customCursOutcome').on('keyup change', function () {
            countAmountByCurs(closestDiv.find('.amount').val(),$(this).val());
        });

        closestDiv.find('.amount').on('keyup change', function () {
            var curs = closestDiv.find('.customCursOutcome').val();
            countAmountByCurs($(this).val(),curs);
        });

        function countAmountByCurs(amount,customCurs) {
            var defaultAmount=(customCurs*amount).toFixed(2);
            closestDiv.find('.defaultAmountOutcome').val(defaultAmount);
        }

        function setCursToCBCurs() {
            var customCurs=(fromCurs/fromNominal)/(toCurs/toNominal);
            closestDiv.find('.customCursOutcome').val(customCurs.toFixed(4));
            countAmountByCurs(closestDiv.find('.amountOutcome').val(),customCurs);
        }
    });
});