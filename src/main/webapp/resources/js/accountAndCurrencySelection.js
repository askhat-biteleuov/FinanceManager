$(document).ready(function() {
    accountSelect();
    $('#accountSelect').on('change focus', function () {
        accountSelect();
    });

    function accountSelect(){
        var toAccountId= $("#accountSelect").val();
        var toAccountObj = $('#'+toAccountId);
        var toCurs = toAccountObj.children('#curs').val();
        var toNominal = toAccountObj.children('#nominal').val();
        $('div[name = currencyCharacterCode]').text(toAccountObj.children("#characterCode").val());
        var fromCurs = $("#fromAccountCurs").val();
        var fromNominal= $("#fromAccountNominal").val();
        var userCurs = $("#userCurrencyCurs").val();
        var userNominal= $("#userCurrencyNominal").val();
        var userCurrencyMultiplier=(fromCurs/fromNominal)/(userCurs/userNominal);
        setCursToCBCurs();

        $('#changeCursCheckBox').click(function () {
            if ($(this).is(':checked')) {
                $('input[name=customCurs]').prop('disabled', false);
            } else {
                setCursToCBCurs();
                $('input[name=customCurs]').prop('disabled', true);
            }
        });

        $('input[name=customCurs]').on('keyup change', function () {
            countAmountByCurs($('#outcomeTransferAmount').val(),$(this).val());
            countAmountInUserCurrency($('#outcomeTransferAmount').val(), userCurrencyMultiplier);
        });

        $('#outcomeTransferAmount').on('keyup change', function () {
            var curs = $('input[name=customCurs]').val();
            countAmountByCurs($(this).val(),curs);
            countAmountInUserCurrency($('#outcomeTransferAmount').val(), userCurrencyMultiplier);
        });

        function countAmountByCurs(amount,customCurs) {
            var defaultAmount=(customCurs*amount).toFixed(2);
            $('#incomeTransferAmount').val(defaultAmount);

        }

        function countAmountInUserCurrency(amount, multiplier) {
            $('input[name=defaultAmount]').val(amount * multiplier);
        }

        function setCursToCBCurs() {
            var curs=(fromCurs/fromNominal)/(toCurs/toNominal);
            $('input[name=customCurs]').val(curs.toFixed(4));
            countAmountByCurs($('#outcomeTransferAmount').val(),curs);
            countAmountInUserCurrency($('#outcomeTransferAmount').val(), userCurrencyMultiplier);
        }
    }
});
