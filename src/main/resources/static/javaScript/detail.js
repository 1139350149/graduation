$(document).ready(function () {
    syncPurchaseInfo();
});

function syncPurchaseInfo() {
    $("#wid").val($("#wid-in").text());
    $("#aid").val($("#aid-in").text());
    $("#price").val($("#price-in").text());
}