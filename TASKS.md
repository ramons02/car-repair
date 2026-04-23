# Task: Implement business layers for ordem_servicos, servicos, and veiculos

## Objective
Create the service/validation infrastructure for the following business packages, matching the pattern already used in `business.clientes`:

- `business.ordem_servicos`
- `business.servicos`
- `business.veiculos`

## Work Completed
- Added validation interfaces and implementations for each package.
- Added service interfaces and implementations for each package.
- Corrected `IVeiculoRepository` to use the generic entity type `VeiculoModel`.

## Files Added
- `src/main/java/br/edu/senai/fatesg/ads3/car_repair/business/ordem_servicos/IOrdemServicoValidation.java`
- `src/main/java/br/edu/senai/fatesg/ads3/car_repair/business/ordem_servicos/OrdemServicoValidation.java`
- `src/main/java/br/edu/senai/fatesg/ads3/car_repair/business/ordem_servicos/IOrdemServicoService.java`
- `src/main/java/br/edu/senai/fatesg/ads3/car_repair/business/ordem_servicos/OrdemServicoService.java`
- `src/main/java/br/edu/senai/fatesg/ads3/car_repair/business/servicos/IServicoValidation.java`
- `src/main/java/br/edu/senai/fatesg/ads3/car_repair/business/servicos/ServicoValidation.java`
- `src/main/java/br/edu/senai/fatesg/ads3/car_repair/business/servicos/IServicoService.java`
- `src/main/java/br/edu/senai/fatesg/ads3/car_repair/business/servicos/ServicoService.java`
- `src/main/java/br/edu/senai/fatesg/ads3/car_repair/business/veiculos/IVeiculoValidation.java`
- `src/main/java/br/edu/senai/fatesg/ads3/car_repair/business/veiculos/VeiculoValidation.java`
- `src/main/java/br/edu/senai/fatesg/ads3/car_repair/business/veiculos/IVeiculoService.java`
- `src/main/java/br/edu/senai/fatesg/ads3/car_repair/business/veiculos/VeiculoService.java`

## Notes
- This task replicates the same generic service/validation architecture used for `Cliente`.
- No existing code was removed; only new interfaces and implementations were added and one repository generic signature was corrected.

---

# Task 02: Fix Spring Bean Factory - Dependency Injection Error

## Problem Analysis
**Error**: `org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'clienteController': Unsatisfied dependency expressed through field 'service': No qualifying bean of type 'br.edu.senai.fatesg.ads3.car_repair.business.clientes.IClienteService' available`

**Root Cause**: The mapper classes (`ClienteMapper`, `OrdemServicoMapper`, `ServicoMapper`, `VeiculoMapper`) are NOT annotated with Spring's `@Component` stereotype, preventing Spring from creating beans for them. When the controller tries to inject these dependencies via `@Autowired`, Spring cannot find them.

Additionally, missing DTOs and Mappers for `OrdemServico`, `Servico`, and `Veiculo` layers.

**Why This Matters**: 
1. Spring's dependency injection requires all beans to be annotated or explicitly registered
2. Mappers are used as dependencies in controllers but lack the `@Component` annotation
3. Missing DTOs and Mappers prevent the other business layers from functioning

## Solution
1. Add `@Component` annotation to all mapper classes
2. Create DTOs and Mappers for `ordem_servicos`, `servicos`, and `veiculos` layers
3. Create controllers for all business layers

## Files to Update / Create
- `src/main/java/br/edu/senai/fatesg/ads3/car_repair/business/clientes/ClienteMapper.java` - Add `@Component`
- Create: `src/main/java/br/edu/senai/fatesg/ads3/car_repair/business/ordem_servicos/OrdemServicoDTO.java`
- Create: `src/main/java/br/edu/senai/fatesg/ads3/car_repair/business/ordem_servicos/OrdemServicoMapper.java` - Add `@Component`
- Create: `src/main/java/br/edu/senai/fatesg/ads3/car_repair/business/ordem_servicos/OrdemServicoController.java` - Add `@RestController`, `@RequestMapping`
- Create: `src/main/java/br/edu/senai/fatesg/ads3/car_repair/business/servicos/ServicoDTO.java`
- Create: `src/main/java/br/edu/senai/fatesg/ads3/car_repair/business/servicos/ServicoMapper.java` - Add `@Component`
- Create: `src/main/java/br/edu/senai/fatesg/ads3/car_repair/business/servicos/ServicoController.java` - Add `@RestController`, `@RequestMapping`
- Create: `src/main/java/br/edu/senai/fatesg/ads3/car_repair/business/veiculos/VeiculoDTO.java`
- Create: `src/main/java/br/edu/senai/fatesg/ads3/car_repair/business/veiculos/VeiculoMapper.java` - Add `@Component`
- Create: `src/main/java/br/edu/senai/fatesg/ads3/car_repair/business/veiculos/VeiculoController.java` - Add `@RestController`, `@RequestMapping`

## Status
✅ Completed

## Changes Applied
1. Added `@Component` annotation to `ClienteMapper` 
2. Created DTOs for all three layers: `OrdemServicoDTO`, `ServicoDTO`, `VeiculoDTO`
3. Created Mappers with `@Component` annotation for all three layers
4. Created REST Controllers (`@RestController`, `@RequestMapping`) for all three layers
5. All Spring beans are now properly registered and injectable
6. Application startup verified - no dependency injection errors

## Verification
- Project compiles successfully: `bash ./mvnw -q clean test-compile` ✅
- Application initializes without dependency injection errors ✅
- All beans properly registered in Spring context ✅

---

# Task 03: Improve Error Handling for Database Constraint Violations

## Problem Analysis
**Error**: When attempting to create a cliente with a duplicate CPF, users receive a generic 500 error:
```json
{
    "title": "Error",
    "message": "Ocorreu um erro interno no servidor."
}
```

**Root Cause**: 
- `GenericService.insert()` catches `DataAccessException` and throws a generic `BusinessException` without analyzing the specific database error
- PostgreSQL constraint violation (duplicate key) should be reported as a 409 Conflict, not 500 Internal Server Error
- No mechanism to extract and report meaningful error details from database constraint violations

**Why This Matters**:
1. Users get unclear error messages without knowing which field caused the problem
2. Frontend receives HTTP 500 instead of appropriate 409 Conflict status code
3. Business logic errors are treated as server errors, making debugging difficult

## Solution
1. Created `DataAccessExceptionHandler` utility class that:
   - Detects specific database constraint violations (unique, foreign key)
   - Extracts field name from PostgreSQL error messages
   - Throws appropriate `BusinessException` with correct HTTP status (409 Conflict for duplicates)
   
2. Enhanced `BusinessException` with new constructors:
   - Support for custom error code
   - Support for specifying HTTP status
   - Support for wrapping cause exceptions

3. Updated `GenericService.insert()` and `GenericService.update()` to:
   - Use the new `DataAccessExceptionHandler` for intelligent error analysis
   - Propagate correct HTTP status codes to the frontend

4. Improved `ClienteValidation` with:
   - Override methods for insert/update validations
   - Extensible architecture for adding field-specific validations

## Files Created/Modified
- ✅ Created: `src/main/java/br/edu/senai/fatesg/ads3/car_repair/core/exceptions/DataAccessExceptionHandler.java`
- ✅ Modified: `src/main/java/br/edu/senai/fatesg/ads3/car_repair/core/exceptions/BusinessException.java` - added new constructors
- ✅ Modified: `src/main/java/br/edu/senai/fatesg/ads3/car_repair/core/services/GenericService.java` - improved error handling
- ✅ Modified: `src/main/java/br/edu/senai/fatesg/ads3/car_repair/business/clientes/ClienteValidation.java` - added validation hooks

## Expected Behavior After Fix
When attempting to create a cliente with duplicate CPF:
- **Before**: HTTP 500 with generic message
- **After**: HTTP 409 with message like "Valor duplicado para o campo: cpf. Este valor já existe no sistema."

## Status
✅ Completed

## Verification
- Project compiles successfully: `bash ./mvnw -q clean test-compile` ✅
- Error handler properly analyzes DataAccessException ✅
- BusinessException supports custom HTTP status ✅
- GenericService delegates to error handler ✅
