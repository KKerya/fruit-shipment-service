const itemsContainer = document.getElementById('items-container');
    const template = document.getElementById('item-template');
    const addItemButton = document.getElementById('add-item');
    const message = document.getElementById('delivery-message');

    function addItemRow() {
        const clone = template.content.cloneNode(true);
        clone.querySelector('.remove-item').addEventListener('click', (event) => {
            event.target.closest('.item-row').remove();
        });
        itemsContainer.appendChild(clone);
    }

    addItemButton.addEventListener('click', addItemRow);
    addItemRow();

    document.getElementById('delivery-form').addEventListener('submit', async (event) => {
        event.preventDefault();
        message.textContent = '';
        message.className = '';

        const supplierId = document.getElementById('supplierId').value;
        const deliveryDate = document.getElementById('deliveryDate').value;
        const items = Array.from(document.querySelectorAll('.item-row')).map(row => ({
            productId: Number(row.querySelector('.product-select').value),
            weightKg: Number(row.querySelector('.weight-input').value)
        }));

        if (!supplierId || !deliveryDate || items.length === 0) {
            message.textContent = 'Заполните все поля.';
            message.className = 'error';
            return;
        }

        const response = await fetch('/api/deliveries', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ supplierId: Number(supplierId), deliveryDate, deliveryItems: items })
        });

        if (!response.ok) {
            message.textContent = 'Не удалось сохранить поставку.';
            message.className = 'error';
            return;
        }

        message.textContent = 'Поставка сохранена. Обновите страницу, чтобы увидеть список.';
        message.className = 'notice';
        document.getElementById('delivery-form').reset();
        itemsContainer.innerHTML = '';
        addItemRow();