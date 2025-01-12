let currentPage = 0;
let currentSize = 10;
let currentTable = 'clients';
let totalElements = 0;
let currentSearchQuery = '';

document.addEventListener('DOMContentLoaded', function () {
    loadData(currentTable, currentPage, currentSize, currentSearchQuery);

    document.querySelectorAll('.nav-link').forEach(tab => {
        tab.addEventListener('click', function () {
            currentTable = this.getAttribute('data-table');
            currentPage = 0;
            currentSearchQuery = '';
            loadData(currentTable, currentPage, currentSize, currentSearchQuery);
        });
    });

    document.getElementById('itemsPerPage').addEventListener('change', function () {
        currentSize = parseInt(this.value);
        const totalPages = Math.ceil(totalElements / currentSize);
        if (currentPage >= totalPages) {
            currentPage = totalPages - 1;
        }
        loadData(currentTable, currentPage, currentSize, currentSearchQuery);
    });

    document.querySelector('.btn.btn-primary').addEventListener('click', function () {
        currentSearchQuery = document.querySelector('input[placeholder="Search..."]').value.trim();
        currentPage = 0;
        if (currentSearchQuery) {
            searchClients(currentSearchQuery);
        } else {
            loadData(currentTable, currentPage, currentSize, currentSearchQuery);
        }
    });
});

function loadData(table, page = 0, size = 10, searchQuery = '') {
    const url = `/database/${table}?page=${page}&size=${size}&search=${encodeURIComponent(searchQuery)}`;

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка загрузки данных');
            }
            return response.json();
        })
        .then(data => {
            totalElements = data.totalElements;
            document.getElementById('tableContent').innerHTML = data.contentHtml;
            renderPagination(data);
        })
        .catch(error => console.error('Ошибка загрузки:', error));
}

function searchClients(query) {
    fetch(`/database/${currentTable}/search?query=${encodeURIComponent(query)}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка поиска');
            }
            return response.json();
        })
        .then(data => {
            document.getElementById('tableContent').innerHTML = generateSearchResultsHtml(data);
            document.getElementById('pagination').innerHTML = '';
        })
        .catch(error => console.error('Ошибка поиска:', error));
}

function generateSearchResultsHtml(data) {
    return data.length > 0
        ? `<ul>${data.map(client => `<li>${client.firstName} ${client.lastName} (ID: ${client.id})</li>`).join('')}</ul>`
        : '<p>Ничего не найдено</p>';
}

function changePage(page) {
    currentPage = page;
    loadData(currentTable, currentPage, currentSize, currentSearchQuery);
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