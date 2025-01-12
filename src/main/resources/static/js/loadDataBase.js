let currentPage = 0;
let currentSize = 10;
let currentTable = 'clients';

document.addEventListener('DOMContentLoaded', function () {
    loadData(currentTable, currentPage, currentSize);

    document.querySelectorAll('.nav-link').forEach(tab => {
        tab.addEventListener('click', function () {
            currentTable = this.getAttribute('data-table');
            currentPage = 0;
            loadData(currentTable, currentPage, currentSize);
        });
    });

    document.getElementById('itemsPerPage').addEventListener('change', function () {
        currentSize = this.value;
        const totalPages = Math.ceil(totalElements / currentSize);
        if (currentPage >= totalPages) {
            currentPage = totalPages - 1;
        }
        loadData(currentTable, currentPage, currentSize);
    });
});

function loadData(table, page = 0, size = 10) {
    fetch(`/database/${table}?page=${page}&size=${size}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка загрузки данных');
            }
            return response.json();
        })
        .then(data => {
            document.getElementById('tableContent').innerHTML = data.contentHtml;
            renderPagination(data);
        })
        .catch(error => console.error('Ошибка загрузки:', error));
}

function changePage(page) {
    currentPage = page;
    loadData(currentTable, currentPage, currentSize);
}

function renderPagination(data) {
    const pagination = document.getElementById('pagination');
    pagination.innerHTML = '';

    if (!data.first) {
        pagination.innerHTML += `<li class="page-item">
                                   <a class="page-link" href="#" onclick="changePage(${data.number - 1})">Previous</a>
                                 </li>`;
    }

    pagination.innerHTML += `<li class="page-item">
                                <select id="pageSelect" class="form-select" onchange="changePage(parseInt(this.value))">
                                    ${Array.from({ length: data.totalPages }, (_, i) => `<option value="${i}" ${i === data.number ? 'selected' : ''}>${i + 1}</option>`).join('')}
                                </select>
                             </li>`;

    if (!data.last) {
        pagination.innerHTML += `<li class="page-item">
                                   <a class="page-link" href="#" onclick="changePage(${data.number + 1})">Next</a>
                                 </li>`;
    }
}

window.onload = function () {
    loadData('clients');
};
