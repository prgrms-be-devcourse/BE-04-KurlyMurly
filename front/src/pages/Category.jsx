import { useState } from 'react';

function Category() {
  const [categories, setCategories] = useState({
    categoryId: '',
    name: '',
  });

  const [items, setItems] = useState([
    { categoryId: 1, name: '채소' },
    { categoryId: 2, name: '과일/견과/쌀' },
    { categoryId: 3, name: '수산/해산/건어물' },
    { categoryId: 4, name: '정육/계란' },
    { categoryId: 5, name: '국/반찬/메인요리' },
    { categoryId: 6, name: '샐러드/간편식' },
    { categoryId: 7, name: '면/양념/오일' },
    { categoryId: 8, name: '생수/음료/우유/커피' },
    { categoryId: 9, name: '와인/위스키/전통주' },
  ]);

  const goToProducts = (categoryId) => {};

  return (
    <div>
      <table border="1" width="500" height="300" align="center">
        <thead>
          <tr>
            <th>
              <b>
                <font size="5">카테고리</font>
              </b>
            </th>
          </tr>
        </thead>
        <tbody>
          {items.map((categories) => (
            <tr key={categories.categoryId} align="center">
              <td>{categories.name}</td>
              <td>
                <button onClick={goToProducts(categories.categoryId)}>이동</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Category;
