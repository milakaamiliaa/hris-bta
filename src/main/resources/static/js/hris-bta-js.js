function dropdownChangePresensi() {
    var input, filter, table, tr, td, i;
    input = document.getElementById("mylist");
    filter = input.value.toUpperCase();
    table = document.getElementById("dataTable");
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[1];
        if (td) {
            if (td.innerHTML.toUpperCase().substring(3,5).localeCompare(filter) === 0){
                tr[i].style.display = "";
            }
            else if (filter == ""){
                tr[i].style.display = "";
            }else {
                tr[i].style.display = "none";
            }
        }
    }
}