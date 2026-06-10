import { useEffect, useState } from 'react'

const API_BASE = 'http://localhost:8080/api'

const C = {
  marino: '#11335E', azul: '#2E6DB4', verde: '#6B9E2F', terracota: '#D97757',
  grafito: '#3F4A52', fondo: '#F7F9FB', tinta: '#1C2733', borde: '#E2E6EB',
}

const MOCK = [{
  id: 0, estado: 'CONFIRMADA', materiaCodigo: '3.1.051', materiaNombre: 'ÁLGEBRA',
  sede: 'LIMA', turno: 'MANIANA', dias: ['LUN', 'MIE'], horaInicio: '08:00',
  horaFin: '12:00', vacantes: 29, profesores: ['Moisés Evaristo Bueno'],
}]

async function api(method, path) {
  const token = localStorage.getItem('token')
  const res = await fetch(API_BASE + path, {
    method,
    headers: { 'Content-Type': 'application/json', ...(token ? { Authorization: `Bearer ${token}` } : {}) },
  })
  if (!res.ok) {
    let msg = `Error ${res.status}`
    try { const d = await res.json(); msg = (d.mensajes && d.mensajes.join(' · ')) || d.error || msg } catch {}
    const e = new Error(msg); e.status = res.status; throw e
  }
  const t = await res.text()
  return t ? JSON.parse(t) : null
}

const estadoColor = (e) => e === 'CONFIRMADA' ? C.verde : e === 'PENDIENTE' ? C.azul : C.grafito

export default function MisInscripcionesPage() {
  const [items, setItems] = useState([])
  const [loading, setLoading] = useState(true)
  const [usingMock, setUsingMock] = useState(false)
  const [error, setError] = useState(null)
  const [busyId, setBusyId] = useState(null)
  const [swapFor, setSwapFor] = useState(null)
  const [destino, setDestino] = useState('')

  const cargar = async () => {
    setLoading(true); setError(null)
    try { setItems(await api('GET', '/inscripciones') || []); setUsingMock(false) }
    catch { setItems(MOCK); setUsingMock(true) }
    finally { setLoading(false) }
  }
  useEffect(() => { cargar() }, [])

  const baja = async (id) => {
    if (usingMock) return alert('Modo demo: logueate para dar de baja.')
    setBusyId(id); setError(null)
    try { await api('DELETE', `/inscripciones/${id}`); await cargar() }
    catch (e) { setError(e.message) } finally { setBusyId(null) }
  }
  const swap = async (id) => {
    if (!destino || usingMock) return usingMock && alert('Modo demo: logueate para cambiar de clase.')
    setBusyId(id); setError(null)
    try { await api('POST', `/inscripciones/${id}/swap/${destino}`); setSwapFor(null); setDestino(''); await cargar() }
    catch (e) { setError(e.message) } finally { setBusyId(null) }
  }

  return (
    <div style={{ background: C.fondo, minHeight: '100vh', color: C.tinta, fontFamily: 'system-ui, sans-serif' }}>
      <header style={{ background: C.marino, borderBottom: `3px solid ${C.verde}`, padding: '14px 24px' }}>
        <span style={{ color: '#fff', fontWeight: 800, letterSpacing: '.04em' }}>UADE</span>
        <span style={{ color: '#9DB2CC', marginLeft: 8 }}>Mis inscripciones</span>
      </header>

      <main style={{ maxWidth: 880, margin: '0 auto', padding: 24 }}>
        {usingMock && (
          <div style={{ background: '#FDEEE8', border: `1px solid ${C.terracota}`, color: '#8a3c22',
            padding: '10px 14px', borderRadius: 10, marginBottom: 16, fontSize: 14 }}>
            Modo demo (sin sesión): mostrando datos de ejemplo. Logueate para datos reales.
          </div>
        )}
        {error && (
          <div style={{ background: '#FDEEE8', border: `1px solid ${C.terracota}`, color: '#8a3c22',
            padding: '10px 14px', borderRadius: 10, marginBottom: 16, fontSize: 14 }}>{error}</div>
        )}

        {loading ? <p style={{ color: C.grafito }}>Cargando…</p>
          : items.length === 0 ? <p style={{ color: C.grafito }}>No tenés inscripciones activas.</p>
          : items.map((i) => (
            <div key={i.id} style={{ background: '#fff', border: `1px solid ${C.borde}`, borderRadius: 12,
              padding: 16, marginBottom: 14, boxShadow: '0 1px 2px rgba(28,39,51,.06)' }}>
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'start', gap: 12 }}>
                <div>
                  <div style={{ fontWeight: 700, fontSize: 16 }}>{i.materiaNombre}</div>
                  <div style={{ color: C.grafito, fontSize: 13 }}>{i.materiaCodigo} · {i.sede} · {i.turno}</div>
                </div>
                <span style={{ background: estadoColor(i.estado), color: '#fff', fontSize: 12,
                  padding: '4px 10px', borderRadius: 999, whiteSpace: 'nowrap' }}>{i.estado}</span>
              </div>

              <div style={{ color: C.tinta, fontSize: 14, marginTop: 8 }}>
                {(i.dias || []).join(' ')} · {i.horaInicio}–{i.horaFin} · {i.vacantes} vacantes
              </div>
              <div style={{ color: C.grafito, fontSize: 13, marginTop: 2 }}>
                {(i.profesores || []).join(', ')}
              </div>

              <div style={{ display: 'flex', gap: 8, marginTop: 14, flexWrap: 'wrap', alignItems: 'center' }}>
                <button onClick={() => baja(i.id)} disabled={busyId === i.id}
                  style={{ background: '#fff', color: C.terracota, border: `1px solid ${C.terracota}`,
                    padding: '7px 14px', borderRadius: 8, cursor: 'pointer' }}>Dar de baja</button>

                {swapFor === i.id ? (
                  <>
                    <input value={destino} onChange={(e) => setDestino(e.target.value)} placeholder="ID clase destino"
                      style={{ padding: '6px 10px', border: `1px solid ${C.borde}`, borderRadius: 8, width: 130 }} />
                    <button onClick={() => swap(i.id)} disabled={busyId === i.id}
                      style={{ background: C.azul, color: '#fff', border: 'none', padding: '7px 14px',
                        borderRadius: 8, cursor: 'pointer' }}>Confirmar</button>
                    <button onClick={() => { setSwapFor(null); setDestino('') }}
                      style={{ background: 'transparent', color: C.grafito, border: 'none', cursor: 'pointer' }}>Cancelar</button>
                  </>
                ) : (
                  <button onClick={() => setSwapFor(i.id)}
                    style={{ background: C.azul, color: '#fff', border: 'none', padding: '7px 14px',
                      borderRadius: 8, cursor: 'pointer' }}>Cambiar clase</button>
                )}
              </div>
            </div>
          ))}
      </main>
    </div>
  )
}