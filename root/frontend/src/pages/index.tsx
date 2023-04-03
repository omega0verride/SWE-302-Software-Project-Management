import {useDispatch, useSelector} from 'react-redux'
import { RootState } from '../store/store'
import React, { useEffect } from 'react'

export default function Home() {

  const user = useSelector((state: RootState)=>state.user)

  const dispatch = useDispatch()

  useEffect(()=>{}, [user]);

  return (
      <div>
          Hello World
      </div>
  )
}
