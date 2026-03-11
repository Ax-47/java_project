async function loadUser() {
  const res = await fetchWithAuth("/api/auth/me")
  if (!res.ok) return
  const data = await res.json()
  document.getElementById("userData").textContent =
    JSON.stringify(data, null, 2)
  loadAdmin()

}

async function loadAdmin(){
  const res = await fetchWithAuth("/api/auth/admin")
  if(!res.ok) return
  const data = await res.json()
  document.getElementById("adminSection").classList.remove("hidden")
  document.getElementById("adminData").textContent =
    JSON.stringify(data,null,2)

}

function logout(){
  localStorage.removeItem("accessToken")
  localStorage.removeItem("refreshToken")
  window.location="/signin"
}

loadUser()
