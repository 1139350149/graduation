$(document).ready(function () {
    onClickForSale();
    selectChange();
    priceFormSubmitBind();
});


function onClickForSale() {
    $('#modal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var id = button.data('id') // Extract info from data-* attributes
        var sale = button.data('sale') // Extract info from data-* attributes
        var price = button.data('price') // Extract info from data-* attributes
        // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
        // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
        var modal = $(this)
        modal.find('#work-id').val(id);
        console.log(id);
        console.log(sale);
        console.log(price);
        console.log(typeof (sale));
        if (sale === true) {
            $("#price").attr("readonly", false);
            $("#price").val(price);
            $("#for-sale").val("true").change();
        } else {
            $("#price").attr("readonly", true);
            $("#price").val("");
            $("#for-sale").val("false").change();
        }
    });
}

function selectChange() {
    $("#for-sale").on("change", function () {
        if ($(this).val() == "true") {
            $("#price").attr("readonly", false);
        } else {
            $("#price").attr("readonly", true);
        }
    });
}

function priceFormSubmitBind() {
    $("#price-btn").on("click", function () {
        $("#price-form").submit();
    });
}