$(document).ready(function () {

    $('.hashtagsSelect').hide();
    $('.date').hide();

    $('.editIncomeBtn').click(function (e) {
        var editDiv = $(this).closest('.editIncomeDiv');

        editDiv.find(".acceptIncomeBtn").show();
        editDiv.find(".cancelIncomeBtn").show();
        $(this).hide();

        var tableRow = $(this).closest('.tableRow');

        var note = tableRow.find(".note");
        note.attr('contenteditable', 'true');
        note.focus();


        var hashtags = tableRow.find(".hashtags");
        hashtags.hide();
        var hashtagsSelect = tableRow.find(".hashtagsSelect");
        hashtagsSelect.select2({
            placeholder: "Хэштеги",
            tags: true,
            width: '100%',
            theme: "bootstrap",
            tokenSeparators: [',', ' ', '#']
        });
        hashtagsSelect.show();

        var incomeDate = tableRow.find(".incomeDate");
        incomeDate.hide();
        var date = tableRow.find(".date");
        date.show();
    });

    $('.acceptIncomeBtn').click(function (e) {
        e.preventDefault();

        var tableRow = $(this).closest('.tableRow');
        var note = tableRow.find(".note").text();
        tableRow.find('.newNote').val(note);

        var form = tableRow.find(".editIncomeForm");
        var data = form.serializeArray();
        var objectifyData = serializeObject(data);
        var jsonData = JSON.stringify(objectifyData);
        $.ajax({
            type: 'POST',
            beforeSend: function (request) {
                var token = $("meta[name='_csrf']").attr("content");
                var header = $("meta[name='_csrf_header']").attr("content");
                request.setRequestHeader(header, token);
            },
            contentType: 'application/json; charset=UTF-8',
            url: form.attr('action'),
            data: jsonData
        }).done(function (data) {
            location.reload();
        })
    });

    $('.cancelIncomeBtn').click(function (e) {
        e.preventDefault();
        var editDiv = $(this).closest('.editIncomeDiv');
        stopEdit(editDiv);
    });

    function stopEdit(editDiv) {

        var tableRow = editDiv.find(".acceptIncomeBtn").closest('.tableRow');
        var note = tableRow.find(".note");
        note.attr('contenteditable', 'false');
        var oldNote = tableRow.find('.oldNote').val().trim();
        note.text(oldNote);


        var select2 = tableRow.find(".select2");
        select2.hide();

        var hashtags = tableRow.find(".hashtags");
        hashtags.show();

        var incomeDate = tableRow.find(".incomeDate");
        incomeDate.show();
        var date = tableRow.find(".date");
        date.hide();

        editDiv.find(".acceptIncomeBtn").hide();
        editDiv.find(".cancelIncomeBtn").hide();
        editDiv.find(".editIncomeBtn").show();

    }

    function serializeObject(formArray) {
        var obj = {};
        $.each(formArray, function () {
            var value = this.value || '';
            if (/^\d+$/.test(value))
                value = +value;

            if (obj[this.name] !== undefined) {
                if (!obj[this.name].push) {
                    obj[this.name] = [obj[this.name]];
                }
                obj[this.name].push(value);
            } else {
                obj[this.name] = value;
            }
        });
        return obj;
    };
});
