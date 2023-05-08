import Select from 'react-select';

const options = [
  { value: 'apple', label: 'Apple' },
  { value: 'banana', label: 'Banana' },
  { value: 'orange', label: 'Orange' }
];

const Test = () => {
  const handleChange = (selectedOptions) => {
    console.log(selectedOptions);
  }

  return (
    <Select
      isMulti
      options={options}
      onChange={handleChange}
    />
  );
}

export default Test;
