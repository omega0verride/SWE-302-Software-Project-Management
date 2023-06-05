import React, { useCallback, useMemo, useState, useEffect } from 'react'
import MaterialReactTable, {
  type MaterialReactTableProps,
  type MRT_Cell,
  type MRT_ColumnDef,
  type MRT_Row,
} from 'material-react-table'
import {
  Box,
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  IconButton,
  MenuItem,
  Stack,
  TextField,
  Tooltip,
} from '@mui/material'
import { Delete, Edit } from '@mui/icons-material'
import {
  getProducts,
  updateProduct,
  productStatus,
  createProduct,
  deleteProduct,
} from './ProductsData'
import BasicModal from './BasicModal'
import Image from 'next/image'
import InputLabel from '@mui/material/InputLabel'
import FormControl from '@mui/material/FormControl'
import Select from '@mui/material/Select'
import Dropzone from 'react-dropzone'
import FormData from 'form-data'
import { uploadImage } from './uploadImage'

export type Product = {
  id: number
  title: string
  description: string
  price: number
  range: number
  discount: number
  used: boolean
  stock: number
  thumbnail: URL
  instagramPostURL: URL
  facebookPostURL: URL
}

const ProductsTable = () => {
  const [createModalOpen, setCreateModalOpen] = useState(false)
  const [tableData, setTableData] = useState<Product[]>([])
  const [imageValue, setImageValue] = useState({})
  useEffect(() => {
    // fetch data
    const dataFetch = async () => {
      const res = await getProducts()
      // set state when the data received
      setTableData(res)
    }

    dataFetch()
  }, [])
  const [validationErrors, setValidationErrors] = useState<{
    [cellId: string]: string
  }>({})

  const handleCreateNewRow = async (values: Product) => {
    const {
      details: { pk_value },
    } = await createProduct(values)
    const response = await uploadImage(values?.thumbnail, pk_value)
    const getProductsFromApi = await getProducts()
    setTableData(getProductsFromApi)
  }

  const handleSaveRowEdits: MaterialReactTableProps<Product>['onEditingRowSave'] =
    async ({ exitEditingMode, row, values }) => {
      if (!Object.keys(validationErrors).length) {
        const newValues = {
          ...values,
          used: values?.used === 'New' ? false : true,
        }
        //console.log(imageValue[0])
        const fd = new FormData()
        fd.append('file', imageValue[0])
        fd.append('name', imageValue[0]?.name)
        const promisesArray = await Promise.all([
          uploadImage(fd, row.getValue('id')),
          updateProduct(row.getValue('id'), newValues),
        ])
        console.log(promisesArray)
        setImageValue({})
        const getProductsFromApi = await getProducts()
        setTableData(getProductsFromApi)
        exitEditingMode() //required to exit editing mode and close modal
      }
    }

  const handleCancelRowEdits = () => {
    setValidationErrors({})
  }

  const handleDeleteRow = useCallback(
    async (row: MRT_Row<Product>) => {
      if (
        !confirm(`Are you sure you want to delete ${row.getValue('title')}`)
      ) {
        return
      }
      //send api delete request here, then refetch or update local table data for re-render
      await deleteProduct(row.getValue('id'))
      const response = await getProducts()
      setTableData(response)
      tableData.splice(row.index, 1)
    },
    [tableData],
  )

  const getCommonEditTextFieldProps = useCallback(
    (
      cell: MRT_Cell<Product>,
    ): MRT_ColumnDef<Product>['muiTableBodyCellEditTextFieldProps'] => {
      return {
        error: !!validationErrors[cell.id],
        helperText: validationErrors[cell.id],
        onBlur: event => {
          const isTitleValid =
            cell.column.id === 'title' && validateRequired(event.target.value)
          const isDescriptionValid =
            cell.column.id === 'description' &&
            validateRequired(event.target.value)
          const isPriceVaild =
            cell.column.id === 'price' && validateRequired(event.target.value)
          if (!isTitleValid && !isPriceVaild && !isDescriptionValid) {
            //set validation error for cell if invalid
            setValidationErrors({
              ...validationErrors,
              [cell.id]: `${cell.column.columnDef.header} is required`,
            })
          } else {
            //remove validation error for cell if valid
            delete validationErrors[cell.id]
            setValidationErrors({
              ...validationErrors,
            })
          }
        },
      }
    },
    [validationErrors],
  )

  const columns = useMemo<MRT_ColumnDef<Product>[]>(
    () => [
      {
        accessorKey: 'id',
        header: 'ID',
        enableColumnOrdering: false,
        enableEditing: false, //disable editing on this column
        enableSorting: false,
        maxSize: 1,
      },
      {
        accessorKey: 'thumbnail',
        header: 'Image',
        size: 20,
        // enableEditing: false,
        enableColumnOrdering: false,
        enableSorting: false,
        Cell: ({ cell }) => (
          <Image
            src={cell.getValue<string>()}
            width={40}
            height={40}
            alt="Picture of the product"
          />
        ),
        Edit: ({ cell, column, table }) => (
          <Dropzone onDrop={acceptedFiles => setImageValue(acceptedFiles)}>
            {({ getRootProps, getInputProps }) => (
              <section>
                <div
                  {...getRootProps()}
                  style={{
                    color: 'gray',
                    borderStyle: 'dashed',
                    borderColor: 'lightgray',
                    paddingLeft: '0.8rem',
                    cursor: 'pointer',
                  }}>
                  <input {...getInputProps()} />
                  <p>
                    {imageValue[0]?.name ??
                      'Drag and drop some files here, or click to select files'}
                  </p>
                </div>
              </section>
            )}
          </Dropzone>
        ),
      },
      {
        accessorKey: 'title',
        header: 'Title',
        size: 20,
        Cell: ({ cell }) => (
          <div>
            {cell.getValue<string>().length > 5 ? (
              <div>{`${cell.getValue<string>().slice(0, 8)}...`}</div>
            ) : cell.getValue<string>().length === 0 ? (
              <div style={{ color: '#D12222' }}>No description</div>
            ) : (
              <div>{cell.getValue<string>()}</div>
            )}
          </div>
        ),
        muiTableBodyCellEditTextFieldProps: ({ cell }) => ({
          ...getCommonEditTextFieldProps(cell),
        }),
      },
      {
        accessorKey: 'description',
        header: 'Description',
        Cell: ({ cell }) => (
          <div>
            {cell.getValue<string>().length > 5 ? (
              <div>{`${cell.getValue<string>().slice(0, 8)}...`}</div>
            ) : cell.getValue<string>().length === 0 ? (
              <div style={{ color: '#D12222' }}>No description</div>
            ) : (
              <div>{cell.getValue<string>()}</div>
            )}
          </div>
        ),
        size: 20,
        muiTableBodyCellEditTextFieldProps: ({ cell }) => ({
          ...getCommonEditTextFieldProps(cell),
        }),
      },
      {
        accessorKey: 'price',
        header: 'Price',
        size: 20,
        muiTableBodyCellEditTextFieldProps: ({ cell }) => ({
          ...getCommonEditTextFieldProps(cell),
          type: 'number',
        }),
      },
      {
        accessorKey: 'range',
        header: 'Range',
        size: 20,
        muiTableBodyCellEditTextFieldProps: ({ cell }) => ({
          type: 'number',
        }),
      },
      {
        accessorKey: 'discount',
        header: 'Discount',
        size: 20,
        muiTableBodyCellEditTextFieldProps: ({ cell }) => ({
          type: 'number',
        }),
      },
      {
        accessorKey: 'used',
        header: 'Prodcut Status',
        maxSize: 20,
        Cell: ({ cell }) => (
          <div>
            {cell.getValue<boolean>() === true ? (
              <div style={{ color: '#D12222' }}>{productStatus[1]}</div>
            ) : (
              <div>{productStatus[0]}</div>
            )}
          </div>
        ),
        muiTableBodyCellEditTextFieldProps: {
          select: true, //change to select for a dropdown
          children: productStatus.map((stat, index) => (
            <MenuItem key={index} value={stat}>
              {stat}
            </MenuItem>
          )),
        },
      },
      {
        accessorKey: 'stock',
        header: 'Stock',
        size: 15,
        muiTableBodyCellEditTextFieldProps: ({ cell }) => ({
          type: 'number',
        }),
      },
      {
        accessorKey: 'instagramPostURL',
        header: 'Instagram Post URL',
        size: 20,
        enableColumnOrdering: false,
        enableSorting: false,
        Cell: ({ cell }) => (
          <div>
            {cell.getValue<URL>() ? (
              <div>
                <a
                  style={{ textDecoration: 'none' }}
                  href={cell.getValue<string>()}
                  target="_blank">
                  Instagram Post
                </a>
              </div>
            ) : (
              <div style={{ color: '#D12222' }}>No URL</div>
            )}
          </div>
        ),
      },
      {
        accessorKey: 'facebookPostURL',
        header: 'Facebook Post URL',
        size: 20,
        enableColumnOrdering: false,
        enableSorting: false,
        Cell: ({ cell }) => (
          <div>
            {cell.getValue<URL>() ? (
              <div>
                <a
                  style={{ textDecoration: 'none' }}
                  href={cell.getValue<string>()}
                  target="_blank">
                  Facebook Post
                </a>
              </div>
            ) : (
              <div style={{ color: '#D12222' }}>No URL</div>
            )}
          </div>
        ),
      },
    ],
    [getCommonEditTextFieldProps, imageValue],
  )

  return (
    <>
      <MaterialReactTable
        muiTableProps={{
          sx: {
            tableLayout: 'fixed',
          },
        }}
        displayColumnDefOptions={{
          'mrt-row-actions': {
            muiTableHeadCellProps: {
              align: 'left',
            },
            size: 1,
          },
        }}
        initialState={{ pagination: { pageSize: 5, pageIndex: 0 } }}
        columns={columns}
        data={tableData}
        editingMode="modal" //default
        enableColumnOrdering
        enableEditing
        onEditingRowSave={handleSaveRowEdits}
        onEditingRowCancel={handleCancelRowEdits}
        renderRowActions={({ row, table }) => (
          <Box sx={{ display: 'flex', gap: '1rem' }}>
            <Tooltip arrow placement="left" title="Edit">
              <IconButton onClick={() => table.setEditingRow(row)}>
                <Edit />
              </IconButton>
            </Tooltip>
            <Tooltip arrow placement="right" title="Delete">
              <IconButton color="error" onClick={() => handleDeleteRow(row)}>
                <Delete />
              </IconButton>
            </Tooltip>
          </Box>
        )}
        renderTopToolbarCustomActions={() => (
          <Button
            onClick={() => setCreateModalOpen(true)}
            variant="contained"
            sx={{
              textTransform: 'inherit',
              backgroundColor: '#D12222',
              color: '#FFF',
              '&:hover': {
                borderColor: '#1E2125',
                boxShadow: 'none',
                backgroundColor: '#1E2125',
              },
            }}>
            Create New Product
          </Button>
        )}
      />
      <CreateNewProductModal
        columns={columns}
        open={createModalOpen}
        onClose={() => setCreateModalOpen(false)}
        onSubmit={handleCreateNewRow}
      />
    </>
  )
}

interface CreateModalProps {
  columns: MRT_ColumnDef<Product>[]
  onClose: () => void
  onSubmit: (values: Product) => void
  open: boolean
}

//example of creating a mui dialog modal for creating new rows
export const CreateNewProductModal = ({
  open,
  columns,
  onClose,
  onSubmit,
}: CreateModalProps) => {
  const [values, setValues] = useState<any>(() =>
    columns.reduce((acc, column) => {
      acc[column.accessorKey ?? ''] = ''
      return acc
    }, {} as any),
  )
  const [errors, setErrors] = useState<string[]>([])

  const handleSubmit = () => {
    //put your validation logic here
    const fd = new FormData()
    const arrayOfErros: string[] = []
    if (!validateRequired(values?.title)) {
      arrayOfErros.push('Please enter a valid title!')
    }
    if (!validateRequired(values?.description)) {
      arrayOfErros.push('Please enter a valid description!')
    }
    if (!validateNumber(values?.price)) {
      arrayOfErros.push('Please enter a valid price!')
    }
    if (values?.file?.length > 1) {
      arrayOfErros.push('Please enter only one file!')
    }
    if (values?.file?.length === 1) {
      const typeOfFile = values?.file[0]?.type?.split('/')[0]
      if (typeOfFile !== 'image') {
        arrayOfErros.push('Please make sure that your file is an image!')
      }

      fd.append('file', values?.file[0])
      fd.append('name', values?.file[0]?.name)
    }
    const newValues = {
      ...values,
      thumbnail: values?.file ? fd : 'images/no_image.jpg/no_image.jpg',
      used: values?.used === 'New' ? false : true,
      price: Number(values?.price),
      discount: values?.discount ? Number(values?.discount) : 0,
      range: values?.range ? Number(values?.range) : 0,
      stock: values?.stock ? Number(values?.stock) : 0,
    }

    delete newValues?.file
    setErrors(arrayOfErros)
    if (arrayOfErros.length === 0) {
      onSubmit(newValues)
      onClose()
      setValues('')
    }
  }

  return (
    <Dialog open={open}>
      <DialogTitle textAlign="center">Create New Product</DialogTitle>
      <DialogContent>
        <form onSubmit={e => e.preventDefault()}>
          <Stack
            sx={{
              width: '100%',
              minWidth: { xs: '300px', sm: '360px', md: '400px' },
              gap: '1.5rem',
            }}>
            <Dropzone
              onDrop={acceptedFiles =>
                setValues({ ...values, file: acceptedFiles })
              }>
              {({ getRootProps, getInputProps }) => (
                <section>
                  <div
                    {...getRootProps()}
                    style={{
                      color: 'gray',
                      borderStyle: 'dashed',
                      borderColor: 'lightgray',
                      paddingLeft: '0.8rem',
                      cursor: 'pointer',
                    }}>
                    <input {...getInputProps()} />
                    <p>
                      {values?.file
                        ? values?.file[0].name
                        : 'Drag and drop some files here, or click to select files'}
                    </p>
                  </div>
                </section>
              )}
            </Dropzone>
            {columns.map((column, index) =>
              column.accessorKey !== 'id' &&
              column.accessorKey !== 'thumbnail' &&
              column.accessorKey !== 'range' &&
              column.accessorKey !== 'price' &&
              column.accessorKey !== 'stock' &&
              column.accessorKey !== 'discount' &&
              column.accessorKey !== 'used' ? (
                <TextField
                  key={index}
                  label={column.header}
                  name={column.accessorKey}
                  onChange={e =>
                    setValues({ ...values, [e.target.name]: e.target.value })
                  }
                />
              ) : (
                column.accessorKey === 'used' && (
                  <FormControl fullWidth>
                    <InputLabel id="demo-simple-select-label">
                      Product Status
                    </InputLabel>
                    <Select
                      labelId="demo-simple-select-label"
                      id={column.accessorKey}
                      key={index}
                      label={column.header}
                      name={column.accessorKey}
                      onChange={e =>
                        setValues({
                          ...values,
                          [e.target.name]: e.target.value,
                        })
                      }>
                      {productStatus.map((status: string, index: number) => (
                        <MenuItem key={index} value={status}>
                          {status}
                        </MenuItem>
                      ))}
                    </Select>
                  </FormControl>
                )
              ),
            )}
            <TextField
              key="price"
              label="Price"
              name="price"
              type="number"
              onChange={e =>
                setValues({ ...values, [e.target.name]: e.target.value })
              }
            />
            <TextField
              key="stock"
              label="Stock"
              name="stock"
              type="number"
              onChange={e =>
                setValues({ ...values, [e.target.name]: e.target.value })
              }
            />
            <TextField
              key="discount"
              label="Discount"
              name="discount"
              type="number"
              onChange={e =>
                setValues({ ...values, [e.target.name]: e.target.value })
              }
            />
            <TextField
              key="range"
              label="Range"
              name="range"
              type="number"
              onChange={e =>
                setValues({ ...values, [e.target.name]: e.target.value })
              }
            />
          </Stack>
        </form>
      </DialogContent>
      <DialogActions sx={{ p: '1.25rem' }}>
        <Button
          onClick={onClose}
          variant="outlined"
          sx={{ borderColor: 'lightblue' }}>
          Cancel
        </Button>
        <Button
          onClick={handleSubmit}
          variant="contained"
          sx={{
            backgroundColor: '#D12222',
            color: '#FFF',
            '&:hover': {
              borderColor: '#1E2125',
              boxShadow: 'none',
              backgroundColor: '#1E2125',
            },
          }}>
          Create New Product
        </Button>
        {errors?.length > 0 && (
          <BasicModal errors={errors} setErrors={setErrors} />
        )}
      </DialogActions>
    </Dialog>
  )
}

const validateRequired = (value: string) => !!value?.length
const validateNumber = (value: number) => value >= 0

export default ProductsTable
