$(document).ready(function() {

    function getAccountProperties(element) {
        var accountId = element.find('.selectedAccount').val();
        element.find('#accountId').val(accountId);
        $.ajax({
            async: false,
            type: 'GET',
            contentType: 'application/json; charset=UTF-8',
            url: "/outcome/add",
            data: ({"accountId": accountId})
        }).done(function (data) {
            element.find(".fromCursOutcome").val(data.currency.curs);
            element.find(".fromNominalOutcome").val(data.currency.nominal);
            element.find(".accountCurOutcome").val(data.currency.characterCode);
        }).fail(function (error) {
        });
    }

    $('.selectedAccount').on('change', function () {
        var outcomeForm = $(this).closest(".outcomeForm");
        getAccountProperties(outcomeForm);
        var toCurs= outcomeForm.find(".toCursOutcome").val();
        var toNominal= outcomeForm.find(".toNominalOutcome").val();
        var fromCurs = outcomeForm.find(".fromCursOutcome").val();
        var fromNominal= outcomeForm.find(".fromNominalOutcome").val();
        var userCur = outcomeForm.find(".userCurOutcome").val();
        var accountCur = outcomeForm.find(".accountCurOutcome").val();

        if (userCur !== accountCur) {
            outcomeForm.find(".currExchangeOutcome").show();
        } else {
            outcomeForm.find(".currExchangeOutcome").hide();
        }
        setCursToCBCurs(outcomeForm);

        function countAmountByCurs(element,amount,customCurs) {
            var defaultAmount=(customCurs*amount).toFixed(2);
            element.find('.defaultAmountOutcome').val(defaultAmount);
        }

        function setCursToCBCurs(element) {
            var customCurs=(fromCurs/fromNominal)/(toCurs/toNominal);
            element.find('.customCursOutcome').val(customCurs.toFixed(4));
            countAmountByCurs(element, element.find('.amountOutcome').val(),customCurs);
        }
    });

    $('.outBtnWithAccounts').on('click', function () {
        var closestDiv = $(this).closest('div');
        getAccountProperties(closestDiv);

        var toCurs= closestDiv.find(".toCursOutcome").val();
        var toNominal= closestDiv.find(".toNominalOutcome").val();
        var fromCurs = closestDiv.find(".fromCursOutcome").val();
        var fromNominal= closestDiv.find(".fromNominalOutcome").val();
        var userCur = closestDiv.find(".userCurOutcome").val();
        var accountCur = closestDiv.find(".accountCurOutcome").val();

        if (userCur !== accountCur) {
            closestDiv.find(".currExchangeOutcome").show();
        }
        setCursToCBCurs(closestDiv);
        closestDiv.find('.checkboxOutcome').click(function () {
            if ($(this).is(':checked')) {
                closestDiv.find('.customCursOutcome').prop('disabled', false);
            } else {
                setCursToCBCurs(closestDiv);
                closestDiv.find('.customCursOutcome').prop('disabled', true);
            }
        });

        closestDiv.find('.customCursOutcome').on('keyup change', function () {
            countAmountByCurs(closestDiv, closestDiv.find('.amountOutcome').val(),$(this).val());
        });

        closestDiv.find('.amountOutcome').on('keyup change', function () {
            var curs = closestDiv.find('.customCursOutcome').val();
            countAmountByCurs(closestDiv, $(this).val(),curs);
        });

        function countAmountByCurs(element,amount,customCurs) {
            var defaultAmount=(customCurs*amount).toFixed(2);
            element.find('.defaultAmountOutcome').val(defaultAmount);
        }

        function setCursToCBCurs(element) {
            var customCurs=(fromCurs/fromNominal)/(toCurs/toNominal);
            element.find('.customCursOutcome').val(customCurs.toFixed(4));
            countAmountByCurs(element, element.find('.amountOutcome').val(),customCurs);
        }
    });

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
            countAmountByCurs(closestDiv.find('.amountOutcome').val(),$(this).val());
        });

        closestDiv.find('.amountOutcome').on('keyup change', function () {
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