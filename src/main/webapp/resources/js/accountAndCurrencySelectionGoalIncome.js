$(document).ready(function() {
    accountSelect();
    $('#accountSelectGoalIncome').on('change focus', function () {
        accountSelect();
    });

    function accountSelect(){
        var toAccountId= $("#accountSelectGoalIncome").val();
        var toAccountObj = $('#Income'+toAccountId);
        var toCurs = toAccountObj.children("#cursGoalIncome").val();
        var toNominal = toAccountObj.children("#nominalGoalIncome").val();
        $('div[name = currencyCharacterCodeIncome]').text(toAccountObj.children("#characterCodeGoalIncome").val());
        var fromCurs = $("#fromAccountCursGoalIncome").val();
        var fromNominal= $("#fromAccountNominalGoalIncome").val();
        var userCurs = $("#userCurrencyCursGoalIncome").val();
        var userNominal= $("#userCurrencyNominalGoalIncome").val();
        var userCurrencyMultiplier=(fromCurs/fromNominal)/(userCurs/userNominal);
        setCursToCBCurs();

        $('#changeCursCheckBoxGoalIncome').click(function () {
            if ($(this).is(':checked')) {
                $('input[name=customCursIncome]').prop('disabled', false);
            } else {
                setCursToCBCurs();
                $('input[name=customCursIncome]').prop('disabled', true);
            }
        });

        $('input[name=customCursIncome]').on('keyup change', function () {
            countAmountByCurs($('#outcomeTransferAmountGoalIncome').val(),$(this).val());
            countAmountInUserCurrency($('#outcomeTransferAmountGoalIncome').val(), userCurrencyMultiplier);
        });

        $('#outcomeTransferAmountGoalIncome').on('keyup change', function () {
            var curs = $('input[name=customCursIncome]').val();
            countAmountByCurs($(this).val(),curs);
            countAmountInUserCurrency($('#outcomeTransferAmountGoalIncome').val(), userCurrencyMultiplier);
        });

        function countAmountByCurs(amount,customCurs) {
            var defaultAmount=(customCurs*amount).toFixed(2);
            $('#incomeTransferAmountGoalIncome').val(defaultAmount);

        }

        function countAmountInUserCurrency(amount, multiplier) {
            $('#defaultTransferAmountGoalIncome').val(amount * multiplier);
        }

        function setCursToCBCurs() {
            var curs=(fromCurs/fromNominal)/(toCurs/toNominal);
            $('input[name=customCursIncome]').val(curs.toFixed(4));
            countAmountByCurs($('#outcomeTransferAmountGoalIncome').val(),curs);
            countAmountInUserCurrency($('#outcomeTransferAmountGoalIncome').val(), userCurrencyMultiplier);
        }
    }
});
