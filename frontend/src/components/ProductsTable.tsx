import React, { useCallback, useMemo, useState } from 'react'
import MaterialReactTable, {
  type MaterialReactTableProps,
  type MRT_Cell,
  type MRT_ColumnDef,
  type MRT_Row
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
  Tooltip
} from '@mui/material'
import { Delete, Edit } from '@mui/icons-material'
import { data, status } from './ProductsData'
import { v4 as uuidv4 } from 'uuid'
import BasicModal from './BasicModal'
import Image from 'next/image'
import InputLabel from '@mui/material/InputLabel'
import FormControl from '@mui/material/FormControl'
import Select, { SelectChangeEvent } from '@mui/material/Select'

export type Product = {
  id: string
  name: string
  code: string
  status: string
  image: string
}

const ProductsTable = () => {
  const [createModalOpen, setCreateModalOpen] = useState(false)
  const [tableData, setTableData] = useState<Product[]>(() => data)
  const [validationErrors, setValidationErrors] = useState<{
    [cellId: string]: string
  }>({})

  const handleCreateNewRow = (values: Product) => {
    tableData.push(values)
    setTableData([...tableData])
  }

  const handleSaveRowEdits: MaterialReactTableProps<Product>['onEditingRowSave'] =
    async ({ exitEditingMode, row, values }) => {
      if (!Object.keys(validationErrors).length) {
        tableData[row.index] = values
        //send/receive api updates here, then refetch or update local table data for re-render
        setTableData([...tableData])
        exitEditingMode() //required to exit editing mode and close modal
      }
    }

  const handleCancelRowEdits = () => {
    setValidationErrors({})
  }

  const handleDeleteRow = useCallback(
    (row: MRT_Row<Product>) => {
      if (!confirm(`Are you sure you want to delete ${row.getValue('name')}`)) {
        return
      }
      //send api delete request here, then refetch or update local table data for re-render
      tableData.splice(row.index, 1)
      setTableData([...tableData])
    },
    [tableData]
  )

  const getCommonEditTextFieldProps = useCallback(
    (
      cell: MRT_Cell<Product>
    ): MRT_ColumnDef<Product>['muiTableBodyCellEditTextFieldProps'] => {
      return {
        error: !!validationErrors[cell.id],
        helperText: validationErrors[cell.id],
        onBlur: (event) => {
          const isValid =
            cell.column.id === 'email' && validateRequired(event.target.value)
          if (!isValid) {
            //set validation error for cell if invalid
            setValidationErrors({
              ...validationErrors,
              [cell.id]: `${cell.column.columnDef.header} is required`
            })
          } else {
            //remove validation error for cell if valid
            delete validationErrors[cell.id]
            setValidationErrors({
              ...validationErrors
            })
          }
        }
      }
    },
    [validationErrors]
  )

  const statusColor = [
    { value: 'IN STOCK', color: '#18CB78' },
    { value: 'LOW STOCK', color: '#FFB82E' },
    { value: 'OUT OF STOCK', color: '#FE5464' }
  ]
  // eslint-disable-next-line react-hooks/exhaustive-deps
  const getStatusColor = (cl) => {
    return statusColor.find((obj) => obj.value === cl).color
  }

  const columns = useMemo<MRT_ColumnDef<Product>[]>(
    () => [
      {
        accessorKey: 'id',
        header: 'ID',
        enableColumnOrdering: false,
        enableEditing: false, //disable editing on this column
        enableSorting: false,
        size: 200
      },
      {
        accessorKey: 'image',
        header: 'Image',
        size: 200,
        enableSorting: false,
        Cell: ({ cell }) => (
          <Image
            src={cell.getValue<string>()}
            width={40}
            height={40}
            alt='Picture of the product'
          />
        )
      },
      {
        accessorKey: 'name',
        header: 'Name',
        size: 200,
        muiTableBodyCellEditTextFieldProps: ({ cell }) => ({
          ...getCommonEditTextFieldProps(cell)
        })
      },
      {
        accessorKey: 'code',
        header: 'Code',
        size: 200,
        muiTableBodyCellEditTextFieldProps: ({ cell }) => ({
          ...getCommonEditTextFieldProps(cell)
        })
      },
      {
        accessorKey: 'status',
        header: 'Status',
        size: 200,
        Cell: ({ cell }) => (
          <div style={{ color: getStatusColor(cell.getValue()) }}>
            {cell.getValue()}
          </div>
        ),
        muiTableBodyCellEditTextFieldProps: {
          select: true, //change to select for a dropdown
          children: status.map((stat) => (
            <MenuItem key={stat} value={stat}>
              {stat}
            </MenuItem>
          ))
        }
      }
    ],
    [getCommonEditTextFieldProps, getStatusColor]
  )

  return (
    <>
      <MaterialReactTable
        displayColumnDefOptions={{
          'mrt-row-actions': {
            muiTableHeadCellProps: {
              align: 'center'
            },
            size: 120
          }
        }}
        columns={columns}
        data={tableData}
        editingMode='modal' //default
        enableColumnOrdering
        enableEditing
        onEditingRowSave={handleSaveRowEdits}
        onEditingRowCancel={handleCancelRowEdits}
        renderRowActions={({ row, table }) => (
          <Box sx={{ display: 'flex', gap: '1rem' }}>
            <Tooltip arrow placement='left' title='Edit'>
              <IconButton onClick={() => table.setEditingRow(row)}>
                <Edit />
              </IconButton>
            </Tooltip>
            <Tooltip arrow placement='right' title='Delete'>
              <IconButton color='error' onClick={() => handleDeleteRow(row)}>
                <Delete />
              </IconButton>
            </Tooltip>
          </Box>
        )}
        renderTopToolbarCustomActions={() => (
          <Button
            onClick={() => setCreateModalOpen(true)}
            variant='contained'
            sx={{
              textTransform: 'inherit',
              backgroundColor: '#D12222',
              color: '#FFF',
              '&:hover': {
                borderColor: '#1E2125',
                boxShadow: 'none',
                backgroundColor: '#1E2125'
              }
            }}
          >
            Create New Product
          </Button>
        )}
      />
      <CreateNewAccountModal
        key={columns}
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
export const CreateNewAccountModal = ({
  open,
  columns,
  onClose,
  onSubmit
}: CreateModalProps) => {
  const [values, setValues] = useState<any>(() =>
    columns.reduce((acc, column) => {
      acc[column.accessorKey ?? ''] = ''
      return acc
    }, {} as any)
  )
  const [errors, setErrors] = useState<string[]>([])

  const handleSubmit = () => {
    //put your validation logic here
    const arrayOfErros: string[] = []
    if (!values?.name) {
      arrayOfErros.push('Please enter a valid name!')
    }
    if (!values?.code) {
      arrayOfErros.push('Please enter a valid code!')
    }
    if (!values?.status) {
      arrayOfErros.push('Please enter a valid status!')
    }
    const newValues = { ...values, id: uuidv4() }

    setErrors(arrayOfErros)
    if (arrayOfErros.length === 0) {
      onSubmit(newValues)
      onClose()
      setValues('')
    }
  }

  return (
    <Dialog open={open}>
      <DialogTitle textAlign='center'>Create New Product</DialogTitle>
      <DialogContent>
        <form onSubmit={(e) => e.preventDefault()}>
          <Stack
            sx={{
              width: '100%',
              minWidth: { xs: '300px', sm: '360px', md: '400px' },
              gap: '1.5rem'
            }}
          >
            {columns.map((column) =>
              column.accessorKey === 'name' || column.accessorKey === 'code' ? (
                <TextField
                  key={column.accessorKey}
                  label={column.header}
                  name={column.accessorKey}
                  onChange={(e) =>
                    setValues({ ...values, [e.target.name]: e.target.value })
                  }
                />
              ) : (
                column.accessorKey === 'status' && (
                  <FormControl fullWidth>
                    <InputLabel id='demo-simple-select-label'>
                      Status
                    </InputLabel>
                    <Select
                      labelId='demo-simple-select-label'
                      id={column.accessorKey}
                      key={column.accessorKey}
                      label={column.header}
                      name={column.accessorKey}
                      onChange={(e) =>
                        setValues({
                          ...values,
                          [e.target.name]: e.target.value
                        })
                      }
                    >
                      {status.map((stat, index) => (
                        <MenuItem key={index} value={stat}>
                          {stat}
                        </MenuItem>
                      ))}
                    </Select>
                  </FormControl>
                )
              )
            )}
          </Stack>
        </form>
      </DialogContent>
      <DialogActions sx={{ p: '1.25rem' }}>
        <Button
          onClick={onClose}
          variant='outlined'
          sx={{ borderColor: 'lightblue' }}
        >
          Cancel
        </Button>
        <Button
          onClick={handleSubmit}
          variant='contained'
          sx={{
            backgroundColor: '#D12222',
            color: '#FFF',
            '&:hover': {
              borderColor: '#1E2125',
              boxShadow: 'none',
              backgroundColor: '#1E2125'
            }
          }}
        >
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

export default ProductsTable
