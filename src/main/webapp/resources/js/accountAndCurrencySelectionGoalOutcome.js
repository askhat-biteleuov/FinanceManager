$(document).ready(function() {
    accountSelect();
    $('#accountSelectGoalOutcome').on('change focus', function () {
        accountSelect();
    });

    function accountSelect(){
        var toAccountId= $("#accountSelectGoalOutcome").val();
        var toAccountObj = $('#Outcome'+toAccountId);
        var toCurs = toAccountObj.children("#cursGoalOutcome").val();
        var toNominal = toAccountObj.children("#nominalGoalOutcome").val();
        $('div[name = currencyCharacterCodeOutcome]').text(toAccountObj.children("#characterCodeGoalOutcome").val());
        var fromCurs = $("#fromAccountCursGoalOutcome").val();
        var fromNominal= $("#fromAccountNominalGoalOutcome").val();
        var userCurs = $("#userCurrencyCursGoalOutcome").val();
        var userNominal= $("#userCurrencyNominalGoalOutcome").val();
        var userCurrencyMultiplier=(fromCurs/fromNominal)/(userCurs/userNominal);
        setCursToCBCurs();

        $('#changeCursCheckBoxGoalOutcome').click(function () {
            if ($(this).is(':checked')) {
                $('input[name=customCursOutcome]').prop('disabled', false);
            } else {
                setCursToCBCurs();
                $('input[name=customCursOutcome]').prop('disabled', true);
            }
        });

        $('input[name=customCursOutcome]').on('keyup change', function () {
            countAmountByCurs($('#outcomeTransferAmountGoalOutcome').val(),$(this).val());
            countAmountInUserCurrency($('#outcomeTransferAmountGoalOutcome').val(), userCurrencyMultiplier);
        });

        $('#outcomeTransferAmountGoalOutcome').on('keyup change', function () {
            var curs = $('input[name=customCursOutcome]').val();
            countAmountByCurs($(this).val(),curs);
            countAmountInUserCurrency($('#outcomeTransferAmountGoalOutcome').val(), userCurrencyMultiplier);
        });

        function countAmountByCurs(amount,customCurs) {
            var defaultAmount=(customCurs*amount).toFixed(2);
            $('#incomeTransferAmountGoalOutcome').val(defaultAmount);

        }

        function countAmountInUserCurrency(amount, multiplier) {
            $('#defaultTransferAmountGoalOutcome').val(amount * multiplier);
        }

        function setCursToCBCurs() {
            var curs=(fromCurs/fromNominal)/(toCurs/toNominal);
            $('input[name=customCursOutcome]').val(curs.toFixed(4));
            countAmountByCurs($('#outcomeTransferAmountGoalOutcome').val(),curs);
            countAmountInUserCurrency($('#outcomeTransferAmountGoalOutcome').val(), userCurrencyMultiplier);
        }
    }
});
