    const itemsContainer = document.getElementById('items-container');
    const template = document.getElementById('item-template');
    const addItemButton = document.getElementById('add-item');
    const message = document.getElementById('delivery-message');

    function addItemRow() {
        const clone = template.content.cloneNode(true);
        const removeBtn = clone.querySelector('.remove-item');
        removeBtn.addEventListener('click', (event) => {
            event.target.closest('.item-row').remove();
        });
        itemsContainer.appendChild(clone);
    }

    addItemRow();

    addItemButton.addEventListener('click', addItemRow);

    document.getElementById('delivery-form').addEventListener('submit', async (event) => {
        event.preventDefault();
        message.textContent = '';
        message.className = '';

        const supplierId = document.getElementById('supplierId').value;
        const deliveryDate = document.getElementById('deliveryDate').value;

        const items = Array.from(document.querySelectorAll('.item-row'))
            .map(row => {
                const weightInput = row.querySelector('.weight-input');
                const weightValue = parseFloat(weightInput.value);
                return {
                    productId: Number(row.querySelector('.product-select').value),
                    weightKg: weightValue > 0 ? weightValue : null
                }
            })
            .filter(item => item.productId && item.weightKg !== null);

        if (!supplierId || !deliveryDate || items.length === 0) {
            message.textContent = 'Заполните все поля и укажите корректный вес (>0).';
            message.className = 'error';
            return;
        }

        try {
            const response = await fetch('/api/deliveries', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ supplierId: Number(supplierId), deliveryDate, deliveryItems: items })
            });

            if (!response.ok) throw new Error('Ошибка сервера');

            message.textContent = 'Поставка сохранена. Обновите страницу, чтобы увидеть список.';
            message.className = 'notice';
            document.getElementById('delivery-form').reset();
            itemsContainer.innerHTML = '';
            addItemRow();
        } catch (e) {
            message.textContent = 'Не удалось сохранить поставку.';
            message.className = 'error';
        }
    });