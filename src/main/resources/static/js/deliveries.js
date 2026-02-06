const reportBody = document.getElementById('report-body');
const message = document.getElementById('report-message');

document.getElementById('report-form').addEventListener('submit', async (event) => {
    event.preventDefault();
    message.textContent = '';
    message.className = '';

    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;

    if (!startDate || !endDate) {
        message.textContent = 'Укажите период отчёта.';
        message.className = 'error';
        return;
    }

    const response = await fetch(`/api/reports/deliveries?startDate=${startDate}&endDate=${endDate}`);
    if (!response.ok) {
        message.textContent = 'Не удалось получить отчёт.';
        message.className = 'error';
        return;
    }

    const data = await response.json();
    reportBody.innerHTML = '';

    if (!data.length) {
        reportBody.innerHTML = '<tr><td colspan="5">Нет данных за выбранный период.</td></tr>';
        return;
    }

    data.forEach(row => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${row.supplierName}</td>
            <td>${row.productName}</td>
            <td>${row.productType}</td>
            <td>${row.totalWeight}</td>
            <td>${row.totalCost}</td>
        `;
        reportBody.appendChild(tr);
    });
});