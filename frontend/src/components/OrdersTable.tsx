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
import { data, status } from './OrdersData'
import { v4 as uuidv4 } from 'uuid'
import BasicModal from './BasicModal'
import dayjs, { Dayjs } from 'dayjs'

export type Order = {
  id: string
  date: string
  user: string
  total: number
  payment: string
  items: string
}

const OrdersTable = () => {
  const [createModalOpen, setCreateModalOpen] = useState(false)
  const [tableData, setTableData] = useState<Order[]>(() => data)
  const [isLoading, setIsLoading] = useState(false)
  const [validationErrors, setValidationErrors] = useState<{
    [cellId: string]: string
  }>({})

  const handleCreateNewRow = (values: Order) => {
    tableData.push(values)
    setTableData([...tableData])
  }

  const handleSaveRowEdits: MaterialReactTableProps<Order>['onEditingRowSave'] =
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
    (row: MRT_Row<Order>) => {
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
      cell: MRT_Cell<Order>
    ): MRT_ColumnDef<Order>['muiTableBodyCellEditTextFieldProps'] => {
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
    { value: 'PAID', color: '#18CB78' },
    { value: 'PENDING', color: '#FFB82E' }
  ]

  // eslint-disable-next-line react-hooks/exhaustive-deps
  const getStatusColor = (cl: string) => {
    return statusColor.find((obj) => obj?.value === cl)?.color
  }

  const columns = useMemo<MRT_ColumnDef<Order>[]>(
    () => [
      {
        accessorKey: 'id',
        header: 'ID',
        enableColumnOrdering: false,
        enableEditing: false, //disable editing on this column
        enableSorting: false,
        size: 150
      },
      {
        accessorKey: 'date',
        header: 'Date',
        size: 150,
        enableSorting: true
      },
      {
        accessorKey: 'user',
        header: 'User',
        size: 200,
        muiTableBodyCellEditTextFieldProps: ({ cell }) => ({
          ...getCommonEditTextFieldProps(cell)
        })
      },
      {
        accessorKey: 'total',
        header: 'Total',
        size: 150,
        muiTableBodyCellEditTextFieldProps: ({ cell }) => ({
          ...getCommonEditTextFieldProps(cell)
        })
      },
      {
        accessorKey: 'payment',
        header: 'Payment',
        size: 200,
        Cell: ({ cell }) => (
          <div style={{ color: getStatusColor(cell.getValue<string>()) }}>
            {cell.getValue<string>()}
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
      },
      {
        accessorKey: 'items',
        header: 'Items',
        size: 200,
        muiTableBodyCellEditTextFieldProps: ({ cell }) => ({
          ...getCommonEditTextFieldProps(cell)
        })
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
        data={tableData ?? []}
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
            Create New Order
          </Button>
        )}
      />
      <CreateNewOrderModal
        columns={columns}
        open={createModalOpen}
        onClose={() => setCreateModalOpen(false)}
        onSubmit={handleCreateNewRow}
      />
    </>
  )
}

interface CreateModalProps {
  columns: MRT_ColumnDef<Order>[]
  onClose: () => void
  onSubmit: (values: Order) => void
  open: boolean
}

//example of creating a mui dialog modal for creating new rows
export const CreateNewOrderModal = ({
  open,
  columns,
  onClose,
  onSubmit
}: CreateModalProps) => {
  const [pickDate, setPickDate] = React.useState<Dayjs | null>(dayjs())
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
    if (!values?.total) {
      arrayOfErros.push('Please enter a valid Total!')
    }
    const newValues = {
      ...values,
      id: uuidv4(),
      date: pickDate?.format('MM/DD/YYYY HH:mm')
    }
    console.log(newValues)

    setErrors(arrayOfErros)
    if (arrayOfErros.length === 0) {
      onSubmit(newValues)
      onClose()
      setValues('')
    }
  }

  return (
    <Dialog open={open}>
      <DialogTitle textAlign='center'>Create New Order</DialogTitle>
      <DialogContent>
        <form onSubmit={(e) => e.preventDefault()}>
          <Stack
            sx={{
              width: '100%',
              minWidth: { xs: '300px', sm: '360px', md: '400px' },
              gap: '1.5rem'
            }}
          >
            {columns?.map((column, index) =>
              column.accessorKey != 'id' && column.accessorKey != 'date' ? (
                <TextField
                  key={index}
                  label={column?.header}
                  name={column?.accessorKey}
                  onChange={(e) =>
                    setValues({ ...values, [e.target.name]: e.target.value })
                  }
                />
              ) : (
                ''
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
          Create New Order
        </Button>
        {errors?.length > 0 && (
          <BasicModal errors={errors} setErrors={setErrors} />
        )}
      </DialogActions>
    </Dialog>
  )
}

const validateRequired = (value: string) => !!value?.length

export default OrdersTable
