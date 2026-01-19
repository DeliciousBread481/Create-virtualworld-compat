# Contributing to Create: VirtualWorld Compat

Thank you for your interest in contributing! This document provides guidelines for contributing to this project.

## How to Contribute

### Reporting Bugs

If you find a bug, please create an issue with:
- A clear, descriptive title
- Detailed steps to reproduce the issue
- Expected vs actual behavior
- Your Minecraft version, mod versions, and crash log (if applicable)
- Screenshots or videos if relevant

### Suggesting Enhancements

Enhancement suggestions are welcome! Please:
- Use a clear, descriptive title
- Provide a detailed description of the proposed feature
- Explain why this enhancement would be useful
- Include examples if applicable

### Pull Requests

1. **Fork the repository** and create your branch from `main`
2. **Follow the code style**:
   - Use 4 spaces for indentation
   - Follow Java naming conventions
   - Add comments for complex logic
   - Keep methods focused and concise

3. **Test your changes**:
   ```bash
   ./gradlew build
   ./gradlew runClient
   ```

4. **Commit your changes**:
   - Use clear, descriptive commit messages
   - Reference issue numbers when applicable
   - Keep commits focused on a single change

5. **Submit your pull request**:
   - Provide a clear description of the changes
   - Link to related issues
   - Explain why the change is needed

## Development Setup

### Prerequisites
- Java 21 JDK
- Git
- An IDE

### Building
```bash
git clone https://github.com/yourusername/create-virtualworld-compat.git
cd create-virtualworld-compat
./gradlew build
```

### Running
```bash
./gradlew runClient
```

## Code Style

- **Indentation**: 4 spaces
- **Braces**: Opening brace on same line
- **Naming**:
  - Classes: PascalCase
  - Methods/Variables: camelCase
  - Constants: UPPER_SNAKE_CASE
- **Documentation**: Add JavaDoc for public methods and classes

## Testing

Before submitting:
1. Build successfully: `./gradlew build`
2. Run client without crashes: `./gradlew runClient`
3. Test contraption assembly with affected block entities
4. Verify no new warnings or errors in logs

## Questions?

Feel free to:
- Open an issue for questions
- Start a discussion in the repository
- Check existing issues for similar questions

Thank you for contributing! 🎉
