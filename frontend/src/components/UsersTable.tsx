import React, { useCallback, useMemo, useState, useEffect } from 'react'
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
  FormControl,
  IconButton,
  InputLabel,
  MenuItem,
  Select,
  Stack,
  TextField,
  Tooltip
} from '@mui/material'
import { Delete, Edit } from '@mui/icons-material'
import {
  authorities,
  userStatus,
  getUsers,
  createUser,
  deleteUser,
  updateUser
} from './UsersCrud'
import BasicModal from './BasicModal'
import { getFromStorage } from '../store/localStorage/manageStorage'

export type Person = {
  id: number
  name: string
  surname: string
  email: string
  phoneNumber: number
  admin: boolean
  password?: string
  enabled: boolean
}

const Table = () => {
  const access_token: string = getFromStorage('access_token')!

  const [createModalOpen, setCreateModalOpen] = useState(false)
  const [tableData, setTableData] = useState<Person[]>([])
  useEffect(() => {
    // fetch data
    const dataFetch = async () => {
      const res = await getUsers(access_token)
      // set state when the data received
      setTableData(res)
    }

    dataFetch()
  }, [])

  const [validationErrors, setValidationErrors] = useState<{
    [cellId: string]: string
  }>({})

  const handleCreateNewRow = async (values: Person) => {
    const { name, surname, email, password, phoneNumber, admin, enabled } =
      values
    const res = await createUser(
      access_token,
      {
        name,
        surname,
        email,
        password,
        phoneNumber: phoneNumber || ''
      },
      admin,
      enabled
    )
    console.log(res)
    const response = await getUsers(access_token)
    setTableData(response)
  }

  const handleSaveRowEdits: MaterialReactTableProps<Person>['onEditingRowSave'] =
    async ({ exitEditingMode, row, values }) => {
      if (!Object.keys(validationErrors).length) {
        tableData[row.index] = values
        //send/receive api updates here, then refetch or update local table data for re-render
        const isAdmin =
          values?.admin === true || values?.admin === 'Admin' ? true : false
        const isEnabled =
          values?.enabled === true || values?.enabled === 'Enabled'
            ? true
            : false
        const { name, surname, phoneNumber } = values
        const res = await updateUser(access_token, row.getValue('email'), {
          name,
          surname,
          phoneNumber,
          isAdmin,
          isEnabled
        })
        console.log(res)
        const response = await getUsers(access_token)
        setTableData(response)
        exitEditingMode() //required to exit editing mode and close modal
      }
    }

  const handleCancelRowEdits = () => {
    setValidationErrors({})
  }

  const handleDeleteRow = useCallback(
    async (row: MRT_Row<Person>) => {
      if (
        !confirm(`Are you sure you want to delete ${row.getValue('email')}`)
      ) {
        return
      }
      //send api delete request here, then refetch or update local table data for re-render
      await deleteUser(access_token, row.getValue('email'))
      const response = await getUsers(access_token)
      setTableData(response)
    },
    [tableData]
  )

  const getCommonEditTextFieldProps = useCallback(
    (
      cell: MRT_Cell<Person>
    ): MRT_ColumnDef<Person>['muiTableBodyCellEditTextFieldProps'] => {
      return {
        error: !!validationErrors[cell.id],
        helperText: validationErrors[cell.id],
        onBlur: (event) => {
          const isValid =
            cell.column.id === 'email'
              ? validateEmail(event.target.value)
              : validateRequired(event.target.value)
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

  const columns = useMemo<MRT_ColumnDef<Person>[]>(
    () => [
      {
        accessorKey: 'id',
        header: 'ID',
        enableColumnOrdering: false,
        enableEditing: false, //disable editing on this column
        enableSorting: false,
        size: 80
      },
      {
        accessorKey: 'name',
        header: 'First Name',
        enableClickToCopy: true,
        size: 140,
        muiTableBodyCellEditTextFieldProps: ({ cell }) => ({
          ...getCommonEditTextFieldProps(cell)
        })
      },
      {
        accessorKey: 'surname',
        header: 'Last Name',
        enableClickToCopy: true,
        size: 140,
        muiTableBodyCellEditTextFieldProps: ({ cell }) => ({
          ...getCommonEditTextFieldProps(cell)
        })
      },
      {
        accessorKey: 'email',
        header: 'Email',
        enableEditing: false,
        enableClickToCopy: true,
        muiTableBodyCellEditTextFieldProps: ({ cell }) => ({
          ...getCommonEditTextFieldProps(cell),
          type: 'email'
        })
      },
      {
        accessorKey: 'phoneNumber',
        header: 'Phone Number',
        enableClickToCopy: true,
        size: 80,
        Cell: ({ cell }) => (
          <div>
            {cell.getValue<string>() || (
              <div style={{ color: '#D12222' }}>{'No phone number'}</div>
            )}
          </div>
        )
      },
      {
        accessorKey: 'admin',
        header: 'Role',
        size: 80,
        Cell: ({ cell }) => (
          <div>
            {cell.getValue<boolean>() === true ? (
              <div style={{ color: '#D12222' }}>{authorities[0]}</div>
            ) : (
              <div>{authorities[1]}</div>
            )}
          </div>
        ),
        muiTableBodyCellEditTextFieldProps: ({ cell }) => ({
          select: true, //change to select for a dropdown
          defaultValue: 'Admin',
          children: authorities.map((authority) => (
            <MenuItem key={authority} value={authority}>
              {authority}
            </MenuItem>
          ))
        })
      },
      {
        accessorKey: 'enabled',
        header: 'Status',
        size: 80,
        Cell: ({ cell }) => (
          <div>
            {cell.getValue<boolean>() === true ? (
              <div style={{ color: '#D12222' }}>{'Enabled'}</div>
            ) : (
              <div>{'Disabled'}</div>
            )}
          </div>
        ),
        muiTableBodyCellEditTextFieldProps: ({ cell }) => ({
          select: true, //change to select for a dropdown
          defaultValue: 'Enabled',
          children: userStatus.map((status, index) => (
            <MenuItem key={index} value={status}>
              {status}
            </MenuItem>
          ))
        })
      }
    ],
    [getCommonEditTextFieldProps]
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
            Create New Account
          </Button>
        )}
      />
      <CreateNewAccountModal
        columns={columns}
        open={createModalOpen}
        onClose={() => setCreateModalOpen(false)}
        onSubmit={handleCreateNewRow}
      />
    </>
  )
}

interface CreateModalProps {
  columns: MRT_ColumnDef<Person>[]
  onClose: () => void
  onSubmit: (values: Person) => void
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
    if (!validateEmail(values?.email)) {
      arrayOfErros.push('Please enter a valid email!')
    }
    if (!validateRequired(values?.name)) {
      arrayOfErros.push('Please enter a valid first name!')
    }
    if (!validateRequired(values?.surname)) {
      arrayOfErros.push('Please enter a valid last name!')
    }
    if (!validateRequired(values?.admin)) {
      arrayOfErros.push('Please enter a valid role!')
    }
    if (!validateRequired(values?.enabled)) {
      arrayOfErros.push('Please enter a valid skip verification!')
    }
    if (!validateRequired(values?.password)) {
      arrayOfErros.push('Please enter a valid password!')
    }
    if (values?.password !== values?.confirmPassword) {
      arrayOfErros.push('Your password must be the same as confirm password!')
    }

    const newValues = {
      ...values,
      admin: values?.admin === 'Admin' ? true : false,
      enabled: values?.enabled === 'Enabled' ? true : false
    }

    setErrors(arrayOfErros)
    if (arrayOfErros.length === 0) {
      onSubmit(newValues)
      onClose()
      setValues('')
    }
  }

  return (
    <Dialog open={open}>
      <DialogTitle textAlign='center'>Create New Account</DialogTitle>
      <DialogContent>
        <form onSubmit={(e) => e.preventDefault()}>
          <Stack
            sx={{
              width: '100%',
              minWidth: { xs: '300px', sm: '360px', md: '400px' },
              gap: '1.5rem'
            }}
          >
            {columns.map((column, index) =>
              column.accessorKey !== 'id' &&
              column.accessorKey !== 'admin' &&
              column.accessorKey !== 'enabled' ? (
                <TextField
                  key={index}
                  label={column.header}
                  name={column.accessorKey}
                  onChange={(e) =>
                    setValues({ ...values, [e.target.name]: e.target.value })
                  }
                />
              ) : (
                column.accessorKey === 'admin' && (
                  <FormControl fullWidth>
                    <InputLabel id='demo-simple-select-label'>Role</InputLabel>
                    <Select
                      labelId='demo-simple-select-label'
                      id={column.accessorKey}
                      key={index}
                      label={column.header}
                      name={column.accessorKey}
                      onChange={(e) =>
                        setValues({
                          ...values,
                          [e.target.name]: e.target.value
                        })
                      }
                    >
                      {authorities.map((auth, index) => (
                        <MenuItem key={index} value={auth}>
                          {auth}
                        </MenuItem>
                      ))}
                    </Select>
                  </FormControl>
                )
              )
            )}
            <FormControl fullWidth>
              <InputLabel id='demo-simple-select-label'>
                Skip Verification
              </InputLabel>
              <Select
                labelId='demo-simple-select-label'
                id={'enabled'}
                key={'enabled'}
                label={'Status'}
                name={'enabled'}
                onChange={(e) =>
                  setValues({
                    ...values,
                    [e.target.name]: e.target.value
                  })
                }
              >
                {userStatus.map((status, index) => (
                  <MenuItem key={index} value={status}>
                    {status === 'Enabled' ? 'True' : 'False'}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
            <TextField
              id='outlined-password-input'
              key={25}
              label='Password'
              name='password'
              type='password'
              onChange={(e) =>
                setValues({ ...values, [e.target.name]: e.target.value })
              }
            />
            <TextField
              id='outlined-password-input'
              key={26}
              label='Confirm Password'
              name='confirmPassword'
              type='password'
              onChange={(e) =>
                setValues({ ...values, [e.target.name]: e.target.value })
              }
            />
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
          Create New Account
        </Button>
        {errors?.length > 0 && (
          <BasicModal errors={errors} setErrors={setErrors} />
        )}
      </DialogActions>
    </Dialog>
  )
}

const validateRequired = (value: string) => !!value?.length
const validateEmail = (email: string) =>
  !!email?.length &&
  email
    .toLowerCase()
    .match(
      /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    )

export default Table
